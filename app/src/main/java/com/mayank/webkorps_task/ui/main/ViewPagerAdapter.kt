package com.mayank.webkorps_task.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mayank.webkorps_task.ui.main.add.AddFragment
import com.mayank.webkorps_task.ui.main.list.ListFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int =  2

    override fun getItem(position: Int): Fragment {
        if(position==0)
            return ListFragment()
        else
            return AddFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(position==0)
            return "List"
        else
            return "Add"
    }
}