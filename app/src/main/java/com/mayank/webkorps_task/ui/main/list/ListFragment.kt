package com.mayank.webkorps_task.ui.main.list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.mayank.webkorps_task.R
import com.mayank.webkorps_task.databinding.FragmentListBinding
import com.mayank.webkorps_task.room.LocaleDataBase
import com.mayank.webkorps_task.room.dao.UserDao
import com.mayank.webkorps_task.room.entity.User
import com.mayank.webkorps_task.ui.main.MainActivity
import com.mayank.webkorps_task.ui.main.add.AddFragment
import com.mayank.webkorps_task.ui.map.MapsActivity


class ListFragment : Fragment(),OnclickCallBack {

    private lateinit var binding: FragmentListBinding
    private lateinit var localeDataBase: LocaleDataBase
    private lateinit var userDao: UserDao
    private lateinit var listAdapter : ListAdapter

    private val list : ArrayList<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment'
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        init()

        return binding.root
    }

    private fun init() {

        localeDataBase = LocaleDataBase.getAppDataBase(context!!)
        userDao = localeDataBase.getUserDao()

        list.addAll(userDao.getUsers())
        listAdapter = ListAdapter(list, this@ListFragment)
        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

        binding.tvNoData.visibility = if (list.size==0) View.VISIBLE else View.GONE


    }




    override fun onClick(position: Int) {
       startActivity(Intent(context, MapsActivity::class.java))
    }

    override fun onEdit(position: Int) {
        return (activity as MainActivity).replace(AddFragment.newInstance("edit",list.get(position).id),"edit")
    }

    override fun onDelete(position: Int) {
        userDao.deleteUser(list.get(position))
        list.removeAt(position)
        listAdapter.notifyDataSetChanged()
        binding.tvNoData.visibility = if (list.size==0) View.VISIBLE else View.GONE
        Toast.makeText(context,"User delete...",Toast.LENGTH_LONG).show()
    }


}