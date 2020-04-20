package com.example.ownspace.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.developer.kalert.KAlertDialog
import com.example.ownspace.R
import com.example.ownspace.models.User
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.header_with_return.*


class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        emailText.text = User().queryFirst()?.email.toString()

        logoutBtn.setOnClickListener {
            logOut()
        }

        back_button.setOnClickListener {
            val homeIntent = Intent(baseContext, MainActivity::class.java)
            homeIntent.putExtra("alreadySignIn", true)
            startActivity(homeIntent)
            finish()
        }

        val contactIntent = Intent(Intent.ACTION_SENDTO).setType("message/rfc822")
        val contactUri = Uri.parse(getString(R.string.mailto))
        contactIntent.data = contactUri

        val contactTextView: TextView = findViewById(R.id.helpBtn)
        contactTextView.setOnClickListener {
            startActivity(
                Intent.createChooser(
                    contactIntent,
                    getString(R.string.toast_contact_support)
                )
            )
        }

    }

    private fun logOut() {
        KAlertDialog(this, KAlertDialog.CUSTOM_IMAGE_TYPE)
            .setCustomImage(R.drawable.ic_log_out)
            .setTitleText(getString(R.string.logout))
            .setContentText(getString(R.string.logout_message))
            .setConfirmText(getString(R.string.logout))
            .setConfirmClickListener {
                User().deleteAll()
                AWSMobileClient.getInstance().signOut()
                val authenticationIntent = Intent(this, AuthenticationActivity::class.java)
                authenticationIntent.putExtra("hasLogOut", true)
                startActivity(authenticationIntent)
                finish()
            }
            .setCancelText(getString(R.string.cancel))
            .showCancelButton(true)
            .confirmButtonColor(R.color.colorPrimaryClient)
            .cancelButtonColor(R.color.colorSecondaryClient)
            .show()
    }
}
