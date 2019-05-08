package com.project.rankers.ui.contest.operation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.project.rankers.ui.contest.operation.dashboard.DashBoardFragment
import com.project.rankers.ui.contest.operation.manager.ManagerFragment
import com.project.rankers.ui.contest.operation.result.ResultContestFragment

class ContestOperationPagerAdapter (fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private var mTabCount: Int = 0
    private var contestId : String? =null
    private var contestDepart : String? = null

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
                fragment = DashBoardFragment.newInstance()
                fragment.arguments = getBundle()
                return fragment
            }
            1 ->{
                fragment = ResultContestFragment.newInstance()
                fragment.arguments = getBundle()
                return fragment
            }
            2 -> {
                fragment = ManagerFragment.newInstance()
                fragment.arguments = getBundle()
                return fragment
            }
            else -> {
                return DashBoardFragment.newInstance()
            }
        }
    }

    fun setBundle(intent: Intent){
        contestId = intent.extras!!.getString("CONTEST_ID")
        contestDepart = intent.extras!!.getString("CONTEST_DEPART")
    }
    private fun getBundle() : Bundle{
        val bundle  =  Bundle()
        bundle.putString("CONTEST_ID",contestId)
        bundle.putString("CONTEST_DEPART",contestDepart)
        return bundle
    }

}