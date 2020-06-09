package com.example.ownspace.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazonaws.mobile.client.AWSMobileClient
import com.amplifyframework.core.Amplify
import com.developer.kalert.KAlertDialog
import com.example.ownspace.R
import com.example.ownspace.adapter.GetDocumentsListAdapter
import com.example.ownspace.api.createFolder
import com.example.ownspace.api.getAllDocuments
import com.example.ownspace.api.uploadFile
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
import java.io.File
import java.io.InputStream


/**
 * The MainActivity class
 */
class MainActivity : AppCompatActivity() {

    init {
        instance = this
    }

    private val currentUser = Amplify.Auth.currentUser

    companion object {
        // Image pick mode
        private val IMAGE_PICK_CODE = 1000

        // Permission code
        private val PERMISSION_CODE = 1001

        val documentList: MutableLiveData<MutableIterable<Any>> by lazy {
            MutableLiveData<MutableIterable<Any>>()
        }

        private var instance: MainActivity? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeIconColor(home)

        val documentsObserver = Observer<MutableIterable<Any>> {
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    recyclerViewDocuments.apply {
                        ConstraintLayout.inflate(
                            context,
                            R.layout.activity_main,
                            null
                        ) as ConstraintLayout
                        layoutManager = LinearLayoutManager(context)
                        adapter = GetDocumentsListAdapter(
                            it,
                            rootView,
                            supportFragmentManager,
                            rootView.homeFrameLayout.id
                        )
                    }
                }
            }
        }

        documentList.observe(this, documentsObserver)

        // Load all the document of the current path
        val currentPath = Path().queryFirst()!!.path
        currentPath[currentPath.size - 1]!!.id?.let { id ->
            getAllDocuments(
                currentUser.userId,
                id
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // If permission denied
                    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    } else {
                        pickImageFromGallery()
                    }
                } else {
                    pickImageFromGallery()
                }
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
                            currentUser.userId,
                            findViewById(android.R.id.content),
                            getString(R.string.toast_folder_created)
                        )
                        createFolderDialog.dismiss()
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

    private fun pickImageFromGallery() {
        // Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission from pop up granted
                    pickImageFromGallery()
                } else {
                    showSnackbar(
                        findViewById(android.R.id.content),
                        getString(R.string.toast_permissions_not_granted), true
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val image = data?.data?.path ?: return
            val inputStream: InputStream? =
                contentResolver.openInputStream(data.data!!)

            // Split the path
            val delimiter = "/"
            val parts = image.split(delimiter)
            // Get the file name
            val fileName = parts.last()
            // Get data for DB
            val currentPath = Path().queryFirst()!!.path
            val parent: String = currentPath[currentPath.size - 1]?.id.toString()

            val file = File(cacheDir, fileName)
            // Create tmp file
            file.createNewFile()
            file.outputStream().use {
                inputStream?.copyTo(it)
            }
            uploadFile(
                file,
                fileName,
                currentUser.userId,
                parent,
                findViewById(android.R.id.content),
                getString(R.string.toast_file_created)
            )
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
