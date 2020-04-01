package com.example.ownspace.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.mobile.client.results.SignInState
import com.example.ownspace.R
import com.example.ownspace.ui.activities.MainActivity
import com.example.ownspace.ui.showSnackbar
import kotlinx.android.synthetic.main.fragment_new_password.*


class NewPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_new_password, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validBtn.setOnClickListener {
            if (newPasswordEditText.text.toString().trim().isEmpty()) {
                closeKeyboard()
                showSnackbar(it, getString(R.string.editText_empty), true)
            } else {
                closeKeyboard()
                confirmSignIn(view, newPasswordEditText.text.toString())
            }
        }
    }

    private fun confirmSignIn(it: View, password: String) {
        AWSMobileClient.getInstance()
            .confirmSignIn(password, object : Callback<SignInResult?> {
                override fun onResult(signInResult: SignInResult?) {
                    Log.d(TAG, "Sign-in callback state: " + signInResult?.signInState)

                    when (signInResult?.signInState) {
                        SignInState.DONE -> showHome()
                        SignInState.SMS_MFA -> showSnackbar(
                            it,
                            getString(R.string.toast_confirm_signin_sms),
                            false
                        )
                        else -> showSnackbar(
                            it,
                            getString(R.string.toast_sign_not_confirmed),
                            false
                        )
                    }
                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()

                }
            })


    }

    private fun showHome() {
        val homeIntent = Intent(context, MainActivity::class.java)
        homeIntent.putExtra("alreadySignIn", false)
        startActivity(homeIntent)
    }

    private fun closeKeyboard() {
        val imm =
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

}
