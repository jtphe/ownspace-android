package com.example.ownspace.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.example.ownspace.R
import com.example.ownspace.ui.showSnackbar


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AWSMobileClient.getInstance().isSignedIn) {
            if (intent?.extras?.getBoolean("alreadySignIn") == false) {
                showSnackbar(
                    findViewById(android.R.id.content),
                    getString(R.string.toast_connected),
                    false
                )
            }

        }

        val homeButton: ImageView = findViewById(R.id.homeIcon)
        val plusButton: ImageView = findViewById(R.id.plusIcon)
        val userButton: ImageView = findViewById(R.id.userIcon)

        homeButton.setOnClickListener {
            Log.d("home", "click")
        }

        plusButton.setOnClickListener {
            Log.d("plus", "click")
        }

        userButton.setOnClickListener {
            val intentUser = Intent(this, UserActivity::class.java)
            startActivity(intentUser)
            finish()
        }

    }
}

