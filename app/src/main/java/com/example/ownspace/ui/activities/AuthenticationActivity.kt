package com.example.ownspace.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.mobile.client.results.SignInState
import com.example.ownspace.R
import com.example.ownspace.ui.fragments.TotpFragment
import com.example.ownspace.ui.showSnackbar
import kotlinx.android.synthetic.main.activity_authentication.*

/**
 * The AuthenticationActivity class
 */
class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        /**
         * Initialize the user's instance of AWS Amplify
         */
        AWSMobileClient.getInstance().initialize(
            applicationContext,
            object : Callback<UserStateDetails> {
                override fun onResult(userStateDetails: UserStateDetails) {
                    Log.i("INIT", "onResult: " + userStateDetails.userState)
                    when (userStateDetails.userState) {
                        UserState.SIGNED_IN -> showHome()
                        UserState.SIGNED_OUT -> if (intent?.extras?.getBoolean("hasLogOut") == true) {
                            showSnackbar(
                                findViewById(android.R.id.content),
                                getString(R.string.toast_logout),
                                false
                            )
                        }
                        else -> showSnackbar(
                            findViewById(android.R.id.content),
                            getString(R.string.toast_undefined_state),
                            false
                        )
                    }
                }

                override fun onError(e: java.lang.Exception) {
                    Log.e("INIT", "Initialization error.", e)
                }
            }
        )

        val contactIntent = Intent(Intent.ACTION_SENDTO).setType("message/rfc822")
        val contactUri = Uri.parse("mailto:ownspaceco@gmail.com")
        contactIntent.data = contactUri

        val contactTextView: TextView = findViewById(R.id.helpBtn)
        contactTextView.setOnClickListener {
            startActivity(Intent.createChooser(contactIntent, "Envoyer un email au support"))
        }

        val btnSignIn: Button = findViewById(R.id.signInBtn)

        /**
         * When user click on the sign in button
         */
        btnSignIn.setOnClickListener {
            closeKeyboard()
            val username: String = emailInput.text.toString()
            val password: String = passwordInput.text.toString()

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                showSnackbar(it, getString(R.string.editText_empty), true)
            } else {
                AWSMobileClient.getInstance().signIn(
                    username,
                    password,
                    null,
                    object : Callback<SignInResult> {
                        override fun onResult(signInResult: SignInResult) {
                            runOnUiThread {
                                Log.d(
                                    FragmentActivity::class.java.simpleName,
                                    "Sign-in callback state: " + signInResult.signInState
                                )
                                when (signInResult.signInState) {
                                    SignInState.DONE -> {
                                        showHome()
                                    }
                                    SignInState.SMS_MFA -> {
                                        showTotpFragment()
                                    }
                                    else -> {
                                        showSnackbar(
                                            it,
                                            getString(R.string.toast_sign_not_confirmed) + signInResult.signInState,
                                            false
                                        )
                                    }
                                }
                            }
                        }

                        override fun onError(e: Exception) {
                            showSnackbar(it, getString(R.string.toast_signin_error), true)
                            Log.e(FragmentActivity::class.java.simpleName, "Sign-in error", e)
                        }
                    })
            }

        }

    }

    /**
     * Show the Main Activity
     */
    private fun showHome() {
        val homeIntent = Intent(this, MainActivity::class.java)
        homeIntent.putExtra("alreadySignIn", false)
        startActivity(homeIntent)
        finish()
        passwordInput.setText("")
    }

    /**
     * Close the keyboard
     */
    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view !== null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Show the ToTpFragment
     */
    private fun showTotpFragment() {
        supportFragmentManager.beginTransaction()
            .replace(authFrameLayout.id, TotpFragment())
            .commit()
    }
}
