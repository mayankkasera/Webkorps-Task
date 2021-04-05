package com.mayank.webkorps_task.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.mayank.webkorps_task.R
import com.mayank.webkorps_task.databinding.ActivityMainBinding
import com.mayank.webkorps_task.ui.main.add.AddFragment
import com.mayank.webkorps_task.ui.main.list.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()

    }

    private fun init() {

        replace(ListFragment())
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("List"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Add"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tab.position==0)
                    return replace(ListFragment())
                else
                    return replace(AddFragment())
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    fun replace(fragment: Fragment) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame, fragment)
        ft.commit()
    }

    fun replace(fragment: Fragment,add : String) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.addToBackStack(add)
        ft.replace(R.id.frame, fragment)
        ft.commit()
    }

}