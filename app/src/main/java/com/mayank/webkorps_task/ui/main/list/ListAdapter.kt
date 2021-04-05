package com.mayank.webkorps_task.ui.main.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mayank.webkorps_task.R
import com.mayank.webkorps_task.databinding.ItemListBinding

class ListAdapter(val onclickCallBack : OnclickCallBack) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    inner class ListViewHolder(val binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.root.setOnClickListener {
                onclickCallBack.onClick(position)
            }
            binding.ivEdit.setOnClickListener {
                onclickCallBack.onEdit(position)
            }
            binding.ivDelete.setOnClickListener {
                onclickCallBack.onDelete(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder = ListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = 7
}