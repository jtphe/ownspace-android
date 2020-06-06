package com.example.ownspace.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Folder
import com.developer.kalert.KAlertDialog
import com.example.ownspace.R
import com.example.ownspace.adapter.GetDocumentsListAdapter
import com.example.ownspace.api.createFolder
import com.example.ownspace.models.Path
import com.example.ownspace.models.User
import com.example.ownspace.ui.showSnackbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
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
import java.io.File

/**
 * The MainActivity class
 */
class MainActivity : AppCompatActivity() {

    private val currentUser = Amplify.Auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeIconColor(home)

        // Load all the document of the current path
        val currentPath = Path().queryFirst()!!.path
        currentPath[currentPath.size - 1]!!.id?.let {
            getAllDocuments(
                currentUser.userId,
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
                        val folderName = createFolderDialog.folderNameInput.text.toString()
                        val folder = File(applicationContext.filesDir, folderName)
                        folder.writeText("")
                        val parent: String = currentPath[currentPath.size - 1]?.id.toString()
                        createFolder(
                            folderName,
                            folder,
                            parent,
                            currentUser.userId
                        )
                        createFolderDialog.dismiss()
                        currentPath[currentPath.size - 1]!!.id?.let {
                            getAllDocuments(
                                currentUser.userId,
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
     * @param user String - The user's id
     * @param parent String - The id of the parent folder in which the user is located
     */
    private fun getAllDocuments(user: String, parent: String) {
        // Create the list that will contain all the documents
        val documentList: MutableList<Any> = mutableListOf()

        // Request to get all the folders
        Amplify.API.query(
            ModelQuery.list(
                Folder::class.java,
                Folder.OWNER.contains(user).and(Folder.PARENT.contains(parent))
            ),
            { response ->
                Log.d("before", "folder")

                response.data.also { it ->
                    if (it !== null) {
                        it.forEach {
                            val tmpFolder = com.example.ownspace.models.Folder()
                            tmpFolder.id = it.id
                            tmpFolder.createdAt = it.createdAt
                            tmpFolder.updatedAt = it.updatedAt
                            tmpFolder.name = it.name
                            tmpFolder.owner = it.owner
                            tmpFolder.isProtected = it.isProtected
                            tmpFolder.parent = it.parent
                            tmpFolder.nbFiles = it.nbFiles
                            tmpFolder.save()
                            documentList.add(tmpFolder)
                        }
                    }
                }
            },
            { error ->
                Log.e("Error =>", error.toString())
            }
        )

        // Request to get all the files
        Amplify.API.query(
            ModelQuery.list(
                com.amplifyframework.datastore.generated.model.File::class.java,
                com.amplifyframework.datastore.generated.model.File.OWNER.contains(user)
                    .and(com.amplifyframework.datastore.generated.model.File.PARENT.contains(parent))
            ),
            { response ->
                Log.d("before", "file")
                response.data.also {
                    GlobalScope.launch {
                        if (it !== null) {
                            it.forEach {
                                val tmpFile = com.example.ownspace.models.File()
                                tmpFile.id = it.id
                                tmpFile.createdAt = it.createdAt
                                tmpFile.updatedAt = it.updatedAt
                                tmpFile.name = it.name
                                tmpFile.content = it.content
                                tmpFile.owner = it.owner
                                tmpFile.isProtected = it.isProtected
                                tmpFile.parent = it.parent
                                tmpFile.size = it.size
                                tmpFile.mimeType = it.mimeType
                                tmpFile.type = it.type
                                tmpFile.save()
                                documentList.add(tmpFile)
                            }
                            withContext(Dispatchers.Main) {
                                recyclerViewDocuments.apply {
                                    ConstraintLayout.inflate(
                                        context,
                                        R.layout.activity_main,
                                        null
                                    ) as ConstraintLayout
                                    layoutManager = LinearLayoutManager(context)
                                    adapter = GetDocumentsListAdapter(
                                        documentList
                                    )
                                }
                            }
                        } else if (documentList.isNotEmpty()) {
                            withContext(Dispatchers.Main) {
                                recyclerViewDocuments.apply {
                                    ConstraintLayout.inflate(
                                        context,
                                        R.layout.activity_main,
                                        null
                                    ) as ConstraintLayout
                                    layoutManager = LinearLayoutManager(context)
                                    adapter = GetDocumentsListAdapter(
                                        documentList
                                    )
                                }
                            }
                        }
                    }
                }
            },
            { error ->
                Log.e("Error =>", error.toString())
            }
        )
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

