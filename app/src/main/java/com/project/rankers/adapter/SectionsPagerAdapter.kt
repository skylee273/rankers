package com.project.rankers.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.rankers.adapter.model.SectionsPagerModel
import com.project.rankers.ui.main.club.ClubFragment
import com.project.rankers.ui.main.contest.ContestFragment
import com.project.rankers.ui.main.message.MessageFragment
import com.project.rankers.ui.main.my.MyFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager), SectionsPagerModel {

    private val itemList = ArrayList<Int>()


    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putBoolean("refresh", true)
        when (position) {
            0 -> {
                val frag =  ContestFragment()
                frag.arguments = bundle
                return frag
            }
            1 -> return ClubFragment()
            2 -> return MessageFragment()
            3 -> return MyFragment()
            else -> ContestFragment()
        }
        return null!!
    }

    // itemList size
    override fun getCount() = itemList.size

    override fun setListItem(position: Int) {
        itemList.add(position)
    }
    override fun getListItem(position: Int) = itemList[position]
}