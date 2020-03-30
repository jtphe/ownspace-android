package com.example.ownspace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.developer.kalert.KAlertDialog


class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val user = AWSMobileClient.getInstance().getUsername()
        val userEmail: TextView = findViewById(R.id.emailText)
        userEmail.text = user.toString()

        val logoutBtn: Button = findViewById(R.id.logoutBtn)

        logoutBtn.setOnClickListener {
            logOut()
        }

        val contactIntent = Intent(Intent.ACTION_SENDTO).setType("message/rfc822")
        val contactUri = Uri.parse(getString(R.string.mailto))
        contactIntent.data = contactUri

        val contactTextView: TextView = findViewById(R.id.helpBtn)
        contactTextView.setOnClickListener {
            startActivity(Intent.createChooser(contactIntent, getString(R.string.toast_contact_support)))
        }

    }

    private fun logOut() {
        KAlertDialog(this, KAlertDialog.CUSTOM_IMAGE_TYPE)
            .setCustomImage(R.drawable.ic_log_out)
            .setTitleText(getString(R.string.logout))
            .setContentText(getString(R.string.logout_message))
            .setConfirmText(getString(R.string.logout))
            .setConfirmClickListener {
                AWSMobileClient.getInstance().signOut()
                val authenticationIntent = Intent(this, AuthenticationActivity::class.java)
                startActivity(authenticationIntent)
                Toast.makeText(
                    applicationContext,
                    getString(R.string.toast_logout),
                    Toast.LENGTH_SHORT
                ).show()}
            .setCancelText(getString(R.string.cancel))
            .showCancelButton(true)
            .confirmButtonColor(R.color.colorPrimaryClient)
            .cancelButtonColor(R.color.colorSecondaryClient)
            .show()


    }
}
