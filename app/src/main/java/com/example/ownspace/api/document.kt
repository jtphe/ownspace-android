package com.example.ownspace.api

import android.util.Log
import com.amazonaws.amplify.generated.graphql.CreateFolderMutation
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient
import com.apollographql.apollo.GraphQLCall
import com.apollographql.apollo.exception.ApolloException
import type.CreateFolderInput
import javax.annotation.Nonnull


/**
 * Create the folder
 * @param name String - The folder name
 * @param owner String - Owner of the file (by default the creator)
 */
fun createFolder(name: String, owner: String, mAWSAppSyncClient: AWSAppSyncClient) {
    val createFolderInput: CreateFolderInput =
        CreateFolderInput.builder().name(name).owner(owner).build()
    mAWSAppSyncClient.mutate(
        CreateFolderMutation.builder().input(createFolderInput).build()
    )
        ?.enqueue(mutationCallback);
}


private val mutationCallback: GraphQLCall.Callback<CreateFolderMutation.Data?> =
    object : GraphQLCall.Callback<CreateFolderMutation.Data?>() {
        override fun onResponse(response: com.apollographql.apollo.api.Response<CreateFolderMutation.Data?>) {
            val data = response.data()?.createFolder()
            Log.d("Folder added =>", data.toString())
        }

        override fun onFailure(@Nonnull e: ApolloException) {
            Log.e("Error", e.toString())
        }
    }