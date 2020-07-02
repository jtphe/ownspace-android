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
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.example.ownspace.R
import com.example.ownspace.models.Path
import com.example.ownspace.models.PathItem
import com.example.ownspace.models.User
import com.example.ownspace.ui.activities.MainActivity
import com.example.ownspace.showSnackbar
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
                                Log.d("clientId =>", clientId)
                                val clientEmail =
                                    AWSMobileClient.getInstance().userAttributes["email"].toString()
                                runOnUiThread {
                                    Log.d(
                                        FragmentActivity::class.java.simpleName,
                                        "Sign-in callback state: " + signInResult.signInState
                                    )
                                    when (signInResult.signInState) {
                                        SignInState.DONE -> {
                                            loadUser(Amplify.Auth.currentUser.userId)
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
                                showSnackbar(
                                    it,
                                    getString(R.string.toast_invalid_token),
                                    true
                                )
                                Log.e(FragmentActivity::class.java.simpleName, "Sign-in error", e)
                            }
                        })
                } else {
                    showSnackbar(
                        view,
                        getString(R.string.toast_no_empty),
                        true
                    )
                }
            } catch (e: Exception) {
                Log.d("Exception => ", e.toString())
            }
        }
    }

    private fun loadUser(id: String) {
        Amplify.API.query(
            ModelQuery.get(com.amplifyframework.datastore.generated.model.User::class.java, id),
            { response ->
                run {
                    // Create the new user object
                    val newUser = User()
                    newUser.id = response.data.id
                    newUser.createdAt = response.data.createdAt
                    newUser.updatedAt = response.data.updatedAt
                    newUser.firstname = response.data.firstname
                    newUser.lastname = response.data.lastname
                    newUser.email = response.data.email
                    newUser.pictureName = response.data.pictureName
                    newUser.pictureUrl = response.data.pictureUrl
                    newUser.notification = response.data.notification
                    newUser.role = response.data.role
                    newUser.group = response.data.group
                    newUser.limitedStorage = response.data.limitedStorage
                    newUser.storageSpaceUsed = response.data.storageSpaceUsed
                    newUser.totalStorageSpace = response.data.totalStorageSpace

                    // Create the root path
                    val home = PathItem()
                    home.id = "-1"
                    home.name = getString(R.string.home_path)
                    // Add the home path to the root path and save to RealmDB
                    Path().apply {
                        path.add(home)
                    }.save()

                    // Save the user and the path to RealmDB
                    newUser.save()
                    home.save()

                    // Go to the home activity
                    showHome()
                }
            },
            { error -> Log.e("MyAmplifyApp", "Query failed", error) }
        )
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
