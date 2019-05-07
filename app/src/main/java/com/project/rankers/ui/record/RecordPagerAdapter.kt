package com.project.rankers.ui.record

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.project.rankers.ui.record.multi.MultiFragment
import com.project.rankers.ui.record.single.SingleFragment

class RecordPagerAdapter (fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private var mTabCount: Int = 0

    init {
        this.mTabCount = 0
    }

    override fun getCount(): Int {
        return mTabCount
    }

    fun setCount(count: Int) {
        mTabCount = count
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = SingleFragment.newInstance()
                return fragment
            }
            1 ->{
                fragment = MultiFragment.newInstance()
                return fragment
            }
            else -> {
                return SingleFragment.newInstance()
            }
        }
    }
}