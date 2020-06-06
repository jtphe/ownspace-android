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
import com.amplifyframework.core.Amplify
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
                Amplify.Auth.signIn(
                    username,
                    password,
                    { result ->
                        if (result.nextStep.signInStep.toString() == "CONFIRM_SIGN_IN_WITH_SMS_MFA_CODE") {
                            showTotpFragment()
                        }
                    },
                    { error ->
                        showSnackbar(it, getString(R.string.toast_signin_error), true)
                        Log.e("Error sign in =>", error.toString())
                    }
                )
            }
        }
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
