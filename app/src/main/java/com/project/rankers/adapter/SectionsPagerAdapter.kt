package com.project.rankers.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.rankers.adapter.model.SectionsPagerModel
import com.project.rankers.ui.fragment.ClubFragment
import com.project.rankers.ui.fragment.ContestFragment
import com.project.rankers.ui.fragment.MessageFragment
import com.project.rankers.ui.fragment.MyFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager), SectionsPagerModel {

    private val itemList = ArrayList<Int>()

    override fun getItem(position: Int) = when (position) {
        0 -> ContestFragment()
        1 -> ClubFragment()
        2 -> MessageFragment()
        3 -> MyFragment()
        else -> ContestFragment()
    }

    // itemList size
    override fun getCount() = itemList.size

    override fun setListItem(position: Int) {
        itemList.add(position)
    }
    override fun getListItem(position: Int) = itemList[position]
}