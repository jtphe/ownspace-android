package com.example.ownspace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StorageAccessLevel
import com.amplifyframework.storage.options.StorageDownloadFileOptions
import com.example.ownspace.R
import com.example.ownspace.models.Folder
import com.example.ownspace.ui.activities.MainActivity
import com.example.ownspace.ui.getCurrentPathString
import com.example.ownspace.ui.openDocument
import kotlinx.android.synthetic.main.document_item_list.view.*
import java.io.File


class GetDocumentsListAdapter(
    private val list: MutableIterable<Any>,
    private val view: View,
    private val supportFragmentManager: FragmentManager,
    private val homeFrameLayout: Int
) : RecyclerView.Adapter<GetDocumentsListAdapter.MyViewHolder>() {

    override fun getItemCount(): Int = list.count()

    private fun getItem(position: Int): Any {
        return list.elementAt(position)
    }

    override fun getItemViewType(position: Int): Int = if (getItem(position) is Folder) 0 else 1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View?
        return if (viewType == 0) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.document_item_list, parent, false)
            FolderViewHolder(view!!);
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.document_item_list, parent, false)
            FileViewHolder(view!!);
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(position, getItem(position))

    abstract inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(position: Int, item: Any)

    }

    inner class FolderViewHolder(val view: View) : MyViewHolder(view) {
        private var _position = 0
        override fun bind(position: Int, item: Any) {
            val folder = item as? Folder ?: return
            _position = position
            view.documentName.text = folder.name
            view.documentIcon.setImageResource(R.drawable.ic_folder)
        }
    }

    inner class FileViewHolder(val view: View) : MyViewHolder(view) {
        private var _position = 0
        override fun bind(position: Int, item: Any) {
            val file = item as? com.example.ownspace.models.File ?: return
            _position = position
            view.documentName.text = file.name
            view.documentIcon.setImageResource(R.drawable.ic_file)
            view.documentRow.setOnClickListener {
                file.name?.let { fileName -> downloadFile(fileName, getCurrentPathString()) }
            }
        }
    }

    private fun downloadFile(name: String, currentPath: String) {
        val options = StorageDownloadFileOptions.builder()
            .accessLevel(StorageAccessLevel.PRIVATE)
            .build()
        val context = MainActivity.applicationContext()

        Amplify.Storage.downloadFile(
            "$currentPath$name",
            File(context.filesDir.toString() + "/$name"),
            options,
            { result ->
                openDocument(
                    result.file.absolutePath.toString(),
                    view,
                    supportFragmentManager,
                    homeFrameLayout,
                    name
                )
                Log.i(
                    "StorageQuickStart",
                    "Successfully downloaded: " + result.file.toString()
                )
            },
            { storageFailure ->
                Log.e(
                    "StorageQuickStart",
                    storageFailure.message,
                    storageFailure
                )
            }
        )
    }
}