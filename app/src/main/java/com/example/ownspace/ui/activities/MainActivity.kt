package com.example.ownspace.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazonaws.amplify.generated.graphql.ListFilesQuery
import com.amazonaws.amplify.generated.graphql.ListFoldersQuery
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers
import com.apollographql.apollo.GraphQLCall
import com.apollographql.apollo.exception.ApolloException
import com.developer.kalert.KAlertDialog
import com.example.ownspace.R
import com.example.ownspace.adapter.GetFilesListAdapter
import com.example.ownspace.adapter.GetFoldersListAdapter
import com.example.ownspace.api.createFolder
import com.example.ownspace.models.Path
import com.example.ownspace.models.User
import com.example.ownspace.ui.showSnackbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.bottom_valid_cancel.*
import kotlinx.android.synthetic.main.create_folder_dialog.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_menu.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_menu.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import type.ModelFileFilterInput
import type.ModelFolderFilterInput
import type.ModelIDInput
import javax.annotation.Nonnull

/**
 * The MainActivity class
 * @property mAWSAppSyncClient AWSAppSyncClient? - The user's instance of AWS Amplify
 * @property folderListCallback Callback<Data?> - The folder list callback
 * @property filesListCallBack Callback<Data?> - The files list callback
 */
class MainActivity : AppCompatActivity() {

    private var mAWSAppSyncClient: AWSAppSyncClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Generate the client that will make call to the API
        mAWSAppSyncClient = AWSAppSyncClient.builder()
            .context(applicationContext)
            .awsConfiguration(AWSConfiguration(applicationContext))
            .build()

        changeIconColor(home)

        val currentPath = Path().queryFirst()!!.path

        currentPath[currentPath.size - 1]!!.id?.let {
            getAllDocuments(
                mAWSAppSyncClient as AWSAppSyncClient, User().queryFirst()?.id.toString(),
                it
            )
        }

        if (AWSMobileClient.getInstance().isSignedIn) {
            if (intent?.extras?.getBoolean("alreadySignIn") == false) {
                showSnackbar(
                    findViewById(android.R.id.content),
                    getString(R.string.toast_connected),
                    false
                )
            }
        } else {
            logOut()
        }

        home.setOnClickListener {
            changeIconColor(home)
        }

        plus.setOnClickListener {
            changeIconColor(plus)
            val bottomSheetDialog = BottomSheetDialog(
                this, R.style.BottomSheetDialogMenuTheme
            )
            val bottomSheetView: View = LayoutInflater.from(applicationContext).inflate(
                R.layout.layout_bottom_sheet_menu,
                bottomSheetContainer
            )

            bottomSheetView.importFileLayout.setOnClickListener {
                Log.d("Import", "File")
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.createFolderLayout.setOnClickListener {
                bottomSheetDialog.dismiss()
                val mBuilder = AlertDialog.Builder(this, R.style.BottomSheetDialogMenuTheme)
                val createFolderView: View = LayoutInflater.from(applicationContext)
                    .inflate(R.layout.create_folder_dialog, null)
                mBuilder.setView(createFolderView)
                val createFolderDialog = mBuilder.create()
                createFolderDialog.show()

                /**
                 * When user click on the cancel button
                 */
                createFolderDialog.cancelBtn.setOnClickListener {
                    createFolderDialog.dismiss()
                    changeIconColor(home)
                }

                /**
                 * When user click on the validate button
                 */
                createFolderDialog.validBtn.setOnClickListener {
                    if (createFolderDialog.folderNameInput.text.trim()
                            .isNotEmpty() && createFolderDialog.folderNameInput.text.length > 3
                    ) {
                        createFolder(
                            createFolderDialog.folderNameInput.text.toString(),
                            User().queryFirst()?.id.toString(),
                            mAWSAppSyncClient as AWSAppSyncClient
                        )
                        createFolderDialog.dismiss()
                        currentPath[currentPath.size - 1]!!.id?.let {
                            getAllDocuments(
                                mAWSAppSyncClient as AWSAppSyncClient,
                                User().queryFirst()?.id.toString(),
                                it
                            )
                        }
                        changeIconColor(home)
                    } else {
                        showSnackbar(
                            window.decorView.mainActivity,
                            getString(R.string.toast_create_folder_not_null),
                            true
                        )
                    }
                }
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        user.setOnClickListener {
            changeIconColor(user)
            val intentUser = Intent(this, UserActivity::class.java)
            startActivity(intentUser)
            finish()
        }
    }


    /**
     * Change the color of the icon selected
     * @param icon ImageView - The icon
     */
    private fun changeIconColor(icon: ImageView) {
        when (icon) {
            home -> {
                home.setColorFilter(resources.getColor(R.color.colorSecondaryClient))
                plus.setColorFilter(resources.getColor(android.R.color.white))
                user.setColorFilter(resources.getColor(android.R.color.white))
                homeDot.visibility = View.VISIBLE
                plusDot.visibility = View.INVISIBLE
                userDot.visibility = View.INVISIBLE
            }
            plus -> {
                home.setColorFilter(resources.getColor(android.R.color.white))
                plus.setColorFilter(resources.getColor(R.color.colorSecondaryClient))
                user.setColorFilter(resources.getColor(android.R.color.white))
                homeDot.visibility = View.INVISIBLE
                plusDot.visibility = View.VISIBLE
                userDot.visibility = View.INVISIBLE
            }
            user -> {
                home.setColorFilter(resources.getColor(android.R.color.white))
                plus.setColorFilter(resources.getColor(android.R.color.white))
                user.setColorFilter(resources.getColor(R.color.colorSecondaryClient))
                homeDot.visibility = View.INVISIBLE
                plusDot.visibility = View.INVISIBLE
                userDot.visibility = View.VISIBLE
            }
            else -> {
                Log.d("Error", "Undefined icon")
            }
        }

    }

    /**
     * Get all the folders from the DataBase
     * @param mAWSAppSyncClient AWSAppSyncClient - The user's instance of AWS Amplify
     */
    private fun getAllDocuments(mAWSAppSyncClient: AWSAppSyncClient, user: String, parent: String) {
        Log.d("user =>", user)
        Log.d("parent =>", parent)
        // Request to get all the folders
        mAWSAppSyncClient.query(
            ListFoldersQuery.builder().filter(
                ModelFolderFilterInput.builder()
                    .owner(ModelIDInput.builder().contains(user).build())
                    .parent(ModelIDInput.builder().contains(parent).build()).build()
            ).limit(100).build()
        ).enqueue(folderListCallback)

        // Request to get all the files
        mAWSAppSyncClient.query(
            ListFilesQuery.builder().filter(
                ModelFileFilterInput.builder()
                    .owner(ModelIDInput.builder().contains(user).build())
                    .parent(ModelIDInput.builder().contains(parent).build()).build()
            ).limit(100).build()
        ).enqueue(filesListCallBack)
    }

    /**
     * Callback of the folder request
     */
    private val folderListCallback: GraphQLCall.Callback<ListFoldersQuery.Data?> =
        object : GraphQLCall.Callback<ListFoldersQuery.Data?>() {
            override fun onResponse(response: com.apollographql.apollo.api.Response<ListFoldersQuery.Data?>) {

                Log.d("response folder =>", response.data()?.listFolders()?.items().toString())
                response.data()?.listFolders()?.items().also {
                    val foldersList = it
                    GlobalScope.launch {
                        if (foldersList!!.isNotEmpty()) {
                            withContext(Dispatchers.Main) {
                                recyclerViewFolders.apply {
                                    layoutManager = LinearLayoutManager(context)
                                    adapter = GetFoldersListAdapter(foldersList)
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(@Nonnull e: ApolloException) {
                Log.e("ERROR", e.toString())
            }
        }


    /**
     * Callback of the file request
     */
    private val filesListCallBack: GraphQLCall.Callback<ListFilesQuery.Data?> =
        object : GraphQLCall.Callback<ListFilesQuery.Data?>() {
            override fun onResponse(response: com.apollographql.apollo.api.Response<ListFilesQuery.Data?>) {
                Log.d("response file =>", response.data()?.listFiles()?.items().toString())
                response.data()?.listFiles()?.items().also {
                    val filesList = it
                    GlobalScope.launch {
                        if (filesList!!.isNotEmpty()) {
                            withContext(Dispatchers.Main) {
                                recyclerViewFiles.apply {
                                    layoutManager = LinearLayoutManager(context)
                                    adapter = GetFilesListAdapter(filesList)
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(@Nonnull e: ApolloException) {
                Log.e("ERROR", e.toString())
            }
        }

    /**
     * Sign out of the application
     */
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

