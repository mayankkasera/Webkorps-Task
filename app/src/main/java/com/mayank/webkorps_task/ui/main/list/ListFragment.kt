package com.mayank.webkorps_task.ui.main.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.mayank.webkorps_task.R
import com.mayank.webkorps_task.databinding.FragmentListBinding
import com.mayank.webkorps_task.ui.main.MainActivity
import com.mayank.webkorps_task.ui.main.add.AddFragment


class ListFragment : Fragment(),OnclickCallBack {

    private lateinit var binding: FragmentListBinding

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
       binding.rvMain.apply {
           layoutManager = LinearLayoutManager(context)
           adapter = ListAdapter(this@ListFragment)
       }
    }



    override fun onClick(position: Int) {

    }

    override fun onEdit(position: Int) {
        return (activity as MainActivity).replace(AddFragment(),"edit")
    }

    override fun onDelete(position: Int) {

    }


}