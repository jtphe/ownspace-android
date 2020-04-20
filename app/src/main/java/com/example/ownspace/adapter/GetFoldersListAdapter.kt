package com.example.ownspace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.amplify.generated.graphql.ListFoldersQuery
import com.example.ownspace.R
import kotlinx.android.synthetic.main.document_item_list.view.*

class GetFoldersListAdapter(private val list: List<ListFoldersQuery.Item>) :
    RecyclerView.Adapter<GetFoldersListAdapter.GetFoldersViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GetFoldersViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.document_item_list,
            parent,
            false
        )
        return GetFoldersViewHolder(view)
    }

    override fun onBindViewHolder(holder: GetFoldersViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class GetFoldersViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(folder: ListFoldersQuery.Item) {
            view.documentName.text = folder.name()
            view.folderIcon.setImageResource(R.drawable.ic_folder)
        }
    }
}
