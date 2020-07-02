package com.example.ownspace.api

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Folder
import com.amplifyframework.storage.StorageAccessLevel
import com.amplifyframework.storage.options.StorageUploadFileOptions
import com.example.ownspace.ui.activities.MainActivity.Companion.documentList
import com.example.ownspace.getCurrentPathString
import com.example.ownspace.showSnackbar
import com.vicpin.krealmextensions.save
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


/**
 * Get all the folders from the DataBase
 * @param user String - The user's id
 * @param parent String - The id of the parent folder in which the user is located
 */
fun getAllDocuments(user: String, parent: String) {
    // Temporary list
    val newDocumentList = mutableListOf<Any>()

    // Request to get all the folders
    Amplify.API.query(
        ModelQuery.list(
            Folder::class.java,
            Folder.OWNER.contains(user).and(Folder.PARENT.contains(parent))
        ),
        { response ->
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
                        newDocumentList.add(tmpFolder)
                    }
                    documentList.postValue(newDocumentList)
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
                            newDocumentList.add(tmpFile)
                        }
                        documentList.postValue(newDocumentList)
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
 * Create the folder
 * @param folderName String - The folder's name
 * @param folder File - The folder object
 * @param owner String - File's owner (by default the creator)
 */
@SuppressLint("LongLogTag")
fun createFolder(folderName: String, folder: File, parent: String, owner: String, view: View, successMessage: String) {
    val options = StorageUploadFileOptions.builder().accessLevel(StorageAccessLevel.PROTECTED).build()
    val currentPathString = getCurrentPathString() + folderName + "/"
    // Temporary list
    val newDocumentList = mutableListOf<Any>()
    // Add the old data to the temporary list
    documentList.value?.forEach { it ->
        newDocumentList.add(it)
    }

    // Create the folder in S3
    Amplify.Storage.uploadFile(currentPathString,
        folder,
        options,
        { result -> Log.i("Creating folder in S3", "Folder created  => " + result.key) },
        { error -> Log.e("Creating folder in S3", "Creation failed", error) })

    // Create the folder in DynamoDB
    val date = System.currentTimeMillis().toString()
    val folderDB = Folder.builder().owner(owner).parent(parent).name(folderName).createdAt(date)
        .updatedAt(date).isProtected(false).nbFiles(0).build()

    Amplify.API.mutate(ModelMutation.create(folderDB),
        { response ->
            Log.i(
                "Creating folder in DynamoDB",
                "Folder created => " + response.data.toString()
            )
            val data = response.data
            // Add to local DB
            val tmpFolder = com.example.ownspace.models.Folder()
            tmpFolder.id = data.id
            tmpFolder.createdAt = data.createdAt
            tmpFolder.updatedAt = data.updatedAt
            tmpFolder.name = data.name
            tmpFolder.owner = data.owner
            tmpFolder.isProtected = data.isProtected
            tmpFolder.parent = data.parent
            tmpFolder.nbFiles = data.nbFiles
            tmpFolder.save()
            newDocumentList.add(tmpFolder)
            // Add to the LiveData
            documentList.postValue(newDocumentList)
            showSnackbar(view, successMessage, true)
        },
        { error -> Log.e("Creating folder in DynamoDB", "Creation failed", error) })
    folder.delete()
}

/**
 * Upload the file
 * @param file File - The file object
 * @param fileName String - The file's name
 * @param owner String - The owner's id
 * @param parent String - The id of the parent folder in which the user is located
 * @param view View - The current view
 * @param successMessage String - The success message if uploaded
 */
@SuppressLint("LongLogTag")
fun uploadFile(file: File, fileName: String, owner: String, parent: String, view: View, successMessage: String) {
    val options = StorageUploadFileOptions.builder().accessLevel(StorageAccessLevel.PROTECTED).build()
    val currentPathString = getCurrentPathString() + fileName
    //Temporary list
    val newDocumentList = mutableListOf<Any>()
    // Add the old data to the temporary list
    documentList.value?.forEach { it ->
        newDocumentList.add(it)
    }

    // Upload file to S3
    Amplify.Storage.uploadFile(
        currentPathString,
        file,
        options,
        { result ->
            Log.i("Uploading file in S3", "File uploaded: " + result.key)
            // If uploaded on S3, then create the file in DynamoDB
            val date = System.currentTimeMillis().toString()
            val size = file.length() / (1024.0 * 1024)

            // Create the file in DynamoDB
            val fileDB =
                com.amplifyframework.datastore.generated.model.File.builder().name(fileName)
                    .owner(owner)
                    .parent(parent).createdAt(date).updatedAt(date).isProtected(false)
                    .type("image/jpeg").mimeType("sprite-brut").size(size.toFloat()).build()

            Amplify.API.mutate(ModelMutation.create(fileDB),
                { response ->
                    Log.i(
                        "Creating file in DynamoDB",
                        "File created => " + response.data.toString()
                    )
                    val data = response.data
                    val tmpFile = com.example.ownspace.models.File()
                    tmpFile.id = data.id
                    tmpFile.createdAt = data.createdAt
                    tmpFile.updatedAt = data.updatedAt
                    tmpFile.name = data.name
                    tmpFile.content = data.content
                    tmpFile.owner = data.owner
                    tmpFile.isProtected = data.isProtected
                    tmpFile.parent = data.parent
                    tmpFile.size = data.size
                    tmpFile.mimeType = data.mimeType
                    tmpFile.type = data.type
                    tmpFile.save()
                    newDocumentList.add(tmpFile)
                    // Add to the LiveData
                    documentList.postValue(newDocumentList)
                    showSnackbar(view, successMessage, true)
                },
                { error -> Log.e("Creating file in DynamoDB", "Creation failed", error) })
            file.delete()
        },
        { error ->
            Log.e("Uploading file in S3", "Upload failed", error)
            file.delete()
        }
    )
}

