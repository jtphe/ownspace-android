package com.example.ownspace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.mobile.client.results.SignInState


class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        AWSMobileClient.getInstance().initialize(
            applicationContext,
            object : Callback<UserStateDetails> {
                override fun onResult(userStateDetails: UserStateDetails) {
                    Log.i("INIT", "onResult: " + userStateDetails.userState)
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

        btnSignIn.setOnClickListener {
            val emailInput: EditText = findViewById(R.id.emailInput)
            val passwordInput: EditText = findViewById(R.id.passwordInput)
            val username: String = emailInput.text.toString()
            val password: String = passwordInput.text.toString()
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
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign-in done.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    showHome()
                                }
                                SignInState.SMS_MFA -> Toast.makeText(
                                    applicationContext,
                                    "Please confirm sign-in with SMS.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                SignInState.NEW_PASSWORD_REQUIRED -> Toast.makeText(
                                    applicationContext,
                                    "Please confirm sign-in with new password.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                else -> Toast.makeText(
                                    applicationContext,
                                    "Unsupported sign-in confirmation: " + signInResult.signInState,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    override fun onError(e: Exception) {
                        Log.e(FragmentActivity::class.java.simpleName, "Sign-in error", e)
                    }
                })

        }

    }

    private fun showHome() {
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
    }
}
