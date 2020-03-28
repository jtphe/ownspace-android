package com.example.ownspace

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.amazonaws.mobile.client.AWSMobileClient

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val user = AWSMobileClient.getInstance().getUsername()
        val userEmail : TextView = findViewById(R.id.emailText)
        userEmail.text = user.toString()

        val logoutBtn: Button = findViewById(R.id.logoutBtn)

        logoutBtn.setOnClickListener {
           logOut()
        }

        val contactIntent = Intent(Intent.ACTION_SENDTO).setType("message/rfc822")
        val contactUri = Uri.parse("mailto:ownspaceco@gmail.com")
        contactIntent.data = contactUri

        val contactTextView: TextView = findViewById(R.id.helpBtn)
        contactTextView.setOnClickListener {
            startActivity(Intent.createChooser(contactIntent, "Envoyer un email au support"))
        }

    }

    private fun logOut(){
        AWSMobileClient.getInstance().signOut()
        val authenticationIntent = Intent(this, AuthenticationActivity::class.java)
        startActivity(authenticationIntent)
        Toast.makeText(
            applicationContext,
            "Vous avez bien été déconnecté(e)",
            Toast.LENGTH_SHORT
        ).show()
    }
}
