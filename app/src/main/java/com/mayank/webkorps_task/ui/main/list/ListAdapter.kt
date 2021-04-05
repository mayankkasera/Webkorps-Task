package com.mayank.webkorps_task.ui.main.list

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mayank.webkorps_task.R
import com.mayank.webkorps_task.databinding.ItemListBinding
import com.mayank.webkorps_task.room.entity.User
import java.text.SimpleDateFormat

class ListAdapter(val list : ArrayList<User>, val onclickCallBack : OnclickCallBack) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    inner class ListViewHolder(val binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.tvName.setText(list.get(position).name)
            binding.tvEmail.setText(list.get(position).email)
            binding.tvDob.setText(SimpleDateFormat("dd-MM-yyyy").format(list.get(position).dob.getTime()))
            binding.ivMain.setImageBitmap(BitmapFactory.decodeByteArray(list.get(position).image, 0, list.get(position).image.size))

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

    override fun getItemCount(): Int = list.size
}