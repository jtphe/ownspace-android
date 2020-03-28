package com.example.ownspace

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.mobile.client.results.SignInState
import com.facebook.drawee.backends.pipeline.Fresco


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fresco.initialize(this)

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
            Log.d("user", "click")
            val intentUser = Intent(this, UserActivity::class.java)
            startActivity(intentUser)
        }

    }

}

