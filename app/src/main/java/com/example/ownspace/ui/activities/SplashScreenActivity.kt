package com.example.ownspace.ui.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.ownspace.R
import com.example.ownspace.ui.showSnackbar
import com.facebook.drawee.backends.pipeline.Fresco
import io.realm.Realm

/**
 * The SplashScreenActivity class
 * @property splashProgress ProgressBar? - The progress bar of the splashscreen
 * @property SPLASH_TIME Int - The time taken by the progress bar to fill entirely
 */
class SplashScreenActivity : AppCompatActivity() {

    var splashProgress: ProgressBar? = null
    var SPLASH_TIME = 2000 //This is 2 seconds


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Fresco.initialize(this)
        Realm.init(this)

        //This is additional feature, used to run a progress bar
        splashProgress = findViewById(R.id.splashProgress)
        playProgress()


        //Code to start timer and take action after the timer ends
        Handler().postDelayed({
            try {
                // Add the AWS plugins
                Amplify.addPlugin(AWSApiPlugin())
                Amplify.addPlugin(AWSCognitoAuthPlugin())
                Amplify.addPlugin(AWSS3StoragePlugin())
                Amplify.configure(applicationContext)

                // Check the current auth session
                Amplify.Auth.fetchAuthSession(
                    { result ->
                        // Check if the user is sign in or not
                        if (!result.isSignedIn) {
                            if (intent?.extras?.getBoolean("hasLogOut") == true) {
                                showSnackbar(
                                    findViewById(android.R.id.content),
                                    getString(R.string.toast_logout),
                                    false
                                )
                            } else {
                                showAuthentication()
                            }
                        } else {
                            showHome()
                        }
                    },
                    { error -> Log.d("Error =>", error.toString()) }
                )
            } catch (error: AmplifyException) {
                Log.e("Error =>", "Could not initialize Amplify", error)
            }
        }, SPLASH_TIME.toLong())
    }

    /**
     * Run the progress bar for 5 seconds
     */
    private fun playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
            .setDuration(2000)
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
    private fun showAuthentication() {
        val authenticationIntent = Intent(this, AuthenticationActivity::class.java)
        startActivity(authenticationIntent)
        finish()
    }
}
