package com.example.ownspace.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.developer.kalert.KAlertDialog
import com.example.ownspace.R
import com.example.ownspace.models.Folder
import com.example.ownspace.models.Path
import com.example.ownspace.models.PathItem
import com.example.ownspace.models.User
import com.vicpin.krealmextensions.deleteAll
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.header_with_return.*

/**
 * The UserActivity class
 */
class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        getUserFromDB()

        emailText.text = Amplify.Auth.currentUser.username

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

    @SuppressLint("SetTextI18n")
    private fun getUserFromDB() {
        val id = Amplify.Auth.currentUser.userId
        Amplify.API.query(
            ModelQuery.get(com.amplifyframework.datastore.generated.model.User::class.java, id),
            { response ->
                run {
                    val firstname = response.data.firstname
                    val lastname = response.data.lastname
                    if (firstname !== null && lastname !== null && firstname.isNotEmpty() && lastname.isNotEmpty()) {
                        userName.text = "$lastname $firstname"
                        lastNameText.text  = lastname
                        firstNameText.text = firstname
                    } else {
                        userName.text = response.data.email
                    }
                }
            },
            { error -> Log.e("Error getUserFromDB =>", "Query failed", error) }
        )

    }

    /**
     * Log out the user from the application
     */
    private fun logOut() {
        KAlertDialog(this, KAlertDialog.CUSTOM_IMAGE_TYPE)
            .setCustomImage(R.drawable.ic_log_out)
            .setTitleText(getString(R.string.logout))
            .setContentText(getString(R.string.logout_message))
            .setConfirmText(getString(R.string.logout))
            .setConfirmClickListener {
                cleanDB()
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

    /**
     * Clean the RealmDB
     */
    private fun cleanDB(){
        User().deleteAll()
        Path().deleteAll()
        PathItem().deleteAll()
        Folder().deleteAll()
    }
}
