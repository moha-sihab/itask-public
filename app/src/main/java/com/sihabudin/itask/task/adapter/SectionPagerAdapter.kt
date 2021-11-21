package com.sihabudin.itask.task.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sihabudin.itask.task.fragment.TaskNextWeekFragment
import com.sihabudin.itask.task.fragment.TaskTodayFragment
import com.sihabudin.itask.task.fragment.TaskTomorrowFragment

class SectionPagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = TaskTodayFragment()
            1 -> fragment = TaskTomorrowFragment()
            2 -> fragment = TaskNextWeekFragment()
        }
        return fragment as Fragment
    }

}