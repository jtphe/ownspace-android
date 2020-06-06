//package com.example.ownspace.adapter
//
//import android.content.Context
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import androidx.fragment.app.FragmentManager
//import androidx.recyclerview.widget.RecyclerView
//import com.amazonaws.amplify.generated.graphql.ListFilesQuery
//import com.amplifyframework.core.Amplify
//import com.amplifyframework.storage.StorageAccessLevel
//import com.amplifyframework.storage.options.StorageDownloadFileOptions
//import com.example.ownspace.R
//import com.example.ownspace.ui.getCurrentPathString
//import com.example.ownspace.ui.openDocument
//import kotlinx.android.synthetic.main.document_item_list.view.*
//import java.util.*
//import kotlin.concurrent.schedule
//
//
///**
// * The GetFilesListAdapter class
// * @property list List<Item> - List of Files
// * @constructor
// */
//class GetFilesListAdapter(
//    private val list: List<ListFilesQuery.Item>,
//    private val context: Context,
//    private val view: View,
//    private val supportFragmentManager: FragmentManager,
//    private val homeFrameLayout: Int
//) :
//    RecyclerView.Adapter<GetFilesListAdapter.GetFilesViewHolder>() {
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): GetFilesViewHolder {
//
//        val view = LayoutInflater.from(parent.context).inflate(
//            R.layout.document_item_list,
//            parent,
//            false
//        )
//        return GetFilesViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: GetFilesViewHolder, position: Int) {
//        holder.bind(list[position])
//    }
//
//    override fun getItemCount(): Int = list.size
//
//    inner class GetFilesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(file: ListFilesQuery.Item) {
//            view.documentName.text = file.name()
//            view.folderIcon.setImageResource(R.drawable.ic_file)
//            view.documentRow.setOnClickListener {
//                downloadFile(file.name(), getCurrentPathString())
//            }
//        }
//    }
//
//    private fun downloadFile(name: String, currentPath: String) {
////        Log.d("name", name)
////        Log.d("currentPath", currentPath)
////        Log.d("context.filesDir", context.filesDir.toString())
//
//        val options = StorageDownloadFileOptions.builder()
//            .accessLevel(StorageAccessLevel.PRIVATE)
//            .build()
//
////        Amplify.Storage.downloadFile(
////            "$currentPath$name",
////            context.filesDir.toString() + "/$name",
////            options,
////            { result ->
////                openDocument(result.getFile().absolutePath.toString(), context, view, supportFragmentManager, homeFrameLayout, name)
////                Log.i(
////                    "StorageQuickStart",
////                    "Successfully downloaded: " + result.getFile().toString()
////                )
////            },
////            { storageFailure ->
////                Log.e(
////                    "StorageQuickStart",
////                    storageFailure.message,
////                    storageFailure
////                )
////            }
////        )
//    }
//}