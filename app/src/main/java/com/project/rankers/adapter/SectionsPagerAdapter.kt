package com.project.rankers.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.project.rankers.adapter.model.SectionsPagerModel
import com.project.rankers.ui.main.contest.ContestFragment
import com.project.rankers.ui.main.message.MessageFragment
import com.project.rankers.ui.main.my.MyFragment
import com.project.rankers.ui.main.rank.RankFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager), SectionsPagerModel {

    private val itemList = ArrayList<Int>()

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return ContestFragment()
            }
            1 -> {
                return RankFragment()
            }
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