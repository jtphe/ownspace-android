package com.example.ownspace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ownspace.R
import com.example.ownspace.models.File
import com.example.ownspace.models.Folder
import kotlinx.android.synthetic.main.document_item_list.view.*

class GetDocumentsListAdapter(private val list: MutableIterable<Any>) : RecyclerView.Adapter<GetDocumentsListAdapter.MyViewHolder>() {

    override fun getItemCount(): Int = list.count()

    private fun getItem(position: Int) : Any {
        return list.elementAt(position)
    }

    override fun getItemViewType(position: Int): Int = if (getItem(position) is Folder) 0 else 1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View?
        return if (viewType == 0) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.document_item_list,parent,false)
            FolderViewHolder(view!!);
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.document_item_list,parent,false)
            FileViewHolder(view!!);
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.bind(position, getItem(position))

    abstract inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(position: Int, item: Any)

    }

    inner class FolderViewHolder( val view: View) : MyViewHolder(view) {
        private var _position = 0
        override fun bind(position: Int, item: Any) {
            val folder = item as? Folder ?: return
            _position = position
            view.documentName.text = folder.name
            view.documentIcon.setImageResource(R.drawable.ic_folder)
        }
    }

    inner class FileViewHolder( val view: View) : MyViewHolder(view) {
        private var _position = 0
        override fun bind(position: Int, item: Any) {
            val file = item as? File ?: return
            _position = position
            view.documentName.text = file.name
            view.documentIcon.setImageResource(R.drawable.ic_file)
        }
    }
}