package com.example.ownspace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.amplify.generated.graphql.ListFilesQuery
import com.example.ownspace.R
import kotlinx.android.synthetic.main.document_item_list.view.*

/**
 * The GetFilesListAdapter class
 * @property list List<Item> - List of Files
 * @constructor
 */
class GetFilesListAdapter(private val list: List<ListFilesQuery.Item>) :
    RecyclerView.Adapter<GetFilesListAdapter.GetFilesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GetFilesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.document_item_list,
            parent,
            false
        )
        return GetFilesViewHolder(view)
    }

    override fun onBindViewHolder(holder: GetFilesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class GetFilesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(file: ListFilesQuery.Item) {
            view.documentName.text = file.name()
            view.folderIcon.setImageResource(R.drawable.ic_file)
        }
    }
}