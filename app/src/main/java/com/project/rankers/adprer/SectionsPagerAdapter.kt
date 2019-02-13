package com.project.rankers.adprer

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.rankers.adprer.model.SectionsPagerModel
import com.project.rankers.views.fragment.ClubFragment
import com.project.rankers.views.fragment.ContestFragement
import com.project.rankers.views.fragment.MessageFragment
import com.project.rankers.views.fragment.MyFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager), SectionsPagerModel {

    private val itemList = ArrayList<Int>()

    override fun getItem(position: Int) = when (position) {
        0 -> ContestFragement()
        1 -> ClubFragment()
        2 -> MessageFragment()
        3 -> MyFragment()
        else -> ContestFragement()
    }

    // itemList size
    override fun getCount() = itemList.size

    override fun setListItem(position: Int) {
        itemList.add(position)
    }
    override fun getListItem(position: Int) = itemList[position]
}