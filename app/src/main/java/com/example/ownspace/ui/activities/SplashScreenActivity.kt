package com.example.ownspace.ui.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.UserStateDetails
import com.example.ownspace.R
import com.example.ownspace.ui.showSnackbar
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_authentication.*


class SplashScreenActivity : AppCompatActivity() {

    var splashProgress: ProgressBar? = null
    var SPLASH_TIME = 3000 //This is 3 seconds


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Fresco.initialize(this)

        //This is additional feature, used to run a progress bar
        splashProgress = findViewById(R.id.splashProgress)
        playProgress()



        //Code to start timer and take action after the timer ends
        Handler().postDelayed(Runnable {    //Do any action here. Now we are moving to next page
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
                            } else {
                                showAuthentication()
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
        }, SPLASH_TIME.toLong())
    }

    /**
     * Run the progress bar for 5 seconds
     */
    private fun playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
            .setDuration(5000)
            .start();
    }

    /**
     * Go to the home activity
     */
    private fun showHome() {
        val homeIntent = Intent(this, MainActivity::class.java)
        homeIntent.putExtra("alreadySignIn", false)
        startActivity(homeIntent)
        finish()
    }

    /**
     * Go to the authentication activity
     */
    private fun showAuthentication(){
        val authenticationIntent = Intent(this, AuthenticationActivity::class.java)
        startActivity(authenticationIntent)
        finish()
    }
}
