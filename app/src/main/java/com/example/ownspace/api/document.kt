package com.example.ownspace.api

import android.annotation.SuppressLint
import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Folder
import com.amplifyframework.storage.StorageAccessLevel
import com.amplifyframework.storage.options.StorageUploadFileOptions
import com.example.ownspace.ui.getCurrentPathString
import java.io.File


/**
 * Create the folder
 * @param folderName String - The folder's name
 * @param folder File - The folder
 * @param owner String - Owner of the file (by default the creator)
 */
@SuppressLint("LongLogTag")
fun createFolder(folderName: String, folder: File, parent: String, owner: String) {
    val options = StorageUploadFileOptions.builder().accessLevel(StorageAccessLevel.PRIVATE).build()
    val currentPathString = getCurrentPathString() + folderName + "/"

    // Create the folder in S3
    Amplify.Storage.uploadFile(currentPathString,
        folder,
        options,
        { result -> Log.i("Create folder in S3", "Folder created  => " + result.key) },
        { error -> Log.e("Create folder in S3", "Upload failed", error) })

    // Create the folder in DynamoDB
    val date = System.currentTimeMillis().toString()
    val folderDB = Folder.builder().owner(owner).parent(parent).name(folderName).createdAt(date)
        .updatedAt(date).build()

    Amplify.API.mutate(ModelMutation.create(folderDB),
        { response -> Log.i("Create folder in DynamoDB", "Folder created => " + response.data.toString()) },
        { error -> Log.e("Create folder in DynamoDB", "Update failed", error) })
}

