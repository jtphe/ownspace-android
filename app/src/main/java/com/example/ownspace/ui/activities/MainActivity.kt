package com.example.ownspace.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.amplify.generated.graphql.CreateFolderMutation
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.apollographql.apollo.GraphQLCall
import com.apollographql.apollo.exception.ApolloException
import com.example.ownspace.R
import com.example.ownspace.models.Folder
import com.example.ownspace.ui.showSnackbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_folder_dialog.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_menu.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_menu.view.*
import type.CreateFolderInput
import javax.annotation.Nonnull


class MainActivity : AppCompatActivity() {

    private var mAWSAppSyncClient: AWSAppSyncClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAWSAppSyncClient = AWSAppSyncClient.builder()
            .context(applicationContext)
            .awsConfiguration(AWSConfiguration(applicationContext))
            .build()
        changeIconColor(homeIcon)

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
            changeIconColor(homeButton)
            Log.d("home", "click")
        }

        plusButton.setOnClickListener {
            changeIconColor(plusButton)
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
                            "7f863e6e-c834-4dca-aec4-89c9d71c0976"
                        )
                        createFolderDialog.dismiss()
                    } else {
                        showSnackbar(it, getString(R.string.toast_create_folder_not_null), true)
                    }
                }
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        userButton.setOnClickListener {
            changeIconColor(userButton)
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
            homeIcon -> {
                homeIcon.setColorFilter(resources.getColor(R.color.colorSecondaryClient))
                plusIcon.setColorFilter(resources.getColor(android.R.color.white))
                userIcon.setColorFilter(resources.getColor(android.R.color.white))
            }
            plusIcon -> {
                homeIcon.setColorFilter(resources.getColor(android.R.color.white))
                plusIcon.setColorFilter(resources.getColor(R.color.colorSecondaryClient))
                userIcon.setColorFilter(resources.getColor(android.R.color.white))
            }
            userIcon -> {
                homeIcon.setColorFilter(resources.getColor(android.R.color.white))
                plusIcon.setColorFilter(resources.getColor(android.R.color.white))
                userIcon.setColorFilter(resources.getColor(R.color.colorSecondaryClient))
            }
            else -> {
                Log.d("Error", "Undefined icon")
            }
        }

    }

    /**
     * Create the folder
     * @param name String - The folder name
     * @param owner String - Owner of the file (by default the creator)
     */
    fun createFolder(name: String, owner: String) {
        val createFolderInput: CreateFolderInput =
            CreateFolderInput.builder().name(name).owner(owner).build()
        mAWSAppSyncClient?.mutate(
            CreateFolderMutation.builder().input(createFolderInput).build()
        )
            ?.enqueue(mutationCallback);
    }


    private val mutationCallback: GraphQLCall.Callback<CreateFolderMutation.Data?> =
        object : GraphQLCall.Callback<CreateFolderMutation.Data?>() {
            override fun onResponse(response: com.apollographql.apollo.api.Response<CreateFolderMutation.Data?>) {
                val data = response.data()?.createFolder()
                val folder = Folder(data!!.id(), data.name(), data.owner())
            }

            override fun onFailure(@Nonnull e: ApolloException) {
                Log.e("Error", e.toString())
            }
        }


}

