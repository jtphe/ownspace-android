package com.example.ownspace.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.mobile.client.results.SignInState
import com.example.ownspace.R
import com.example.ownspace.models.User
import com.example.ownspace.ui.activities.MainActivity
import com.example.ownspace.ui.showSnackbar
import com.vicpin.krealmextensions.save
import kotlinx.android.synthetic.main.fragment_totp.*

/**
 * The TotpFragment class
 */
class TotpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_totp, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validBtn.setOnClickListener {
            try {
                if (totpToken.text.trim().isNotEmpty()) {
                    /**
                     * Confirm the user sign in on AWS Amplify
                     */
                    AWSMobileClient.getInstance().confirmSignIn(
                        totpToken.text.toString(),
                        object : Callback<SignInResult> {
                            override fun onResult(signInResult: SignInResult) {
                                val clientId =
                                    AWSMobileClient.getInstance().userAttributes["sub"]
                                        .toString()
                                val clientEmail =
                                    AWSMobileClient.getInstance().userAttributes["email"].toString()
                                runOnUiThread {
                                    Log.d(
                                        FragmentActivity::class.java.simpleName,
                                        "Sign-in callback state: " + signInResult.signInState
                                    )
                                    when (signInResult.signInState) {
                                        SignInState.DONE -> {
                                            // Create the new user object
                                            val newUser = User()
                                            newUser.id = clientId
                                            newUser.email = clientEmail
                                            // Add the user object to RealmDB
                                            newUser.save()
                                            // Go to the home activity
                                            showHome()
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
                                showSnackbar(it, getString(R.string.toast_invalid_token), true)
                                Log.e(FragmentActivity::class.java.simpleName, "Sign-in error", e)
                            }
                        })
                } else {
                    showSnackbar(view, getString(R.string.toast_no_empty), true)
                }
            } catch (e: Exception) {
                Log.d("Exception => ", e.toString())
            }
        }
    }

    /**
     * Show the Main Activity
     */
    private fun showHome() {
        val homeIntent = Intent(activity, MainActivity::class.java)
        homeIntent.putExtra("alreadySignIn", false)
        activity?.startActivity(homeIntent)
        activity?.finish()
    }

}
