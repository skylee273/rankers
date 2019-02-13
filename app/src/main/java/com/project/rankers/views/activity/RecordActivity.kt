package com.project.rankers.views.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.project.rankers.R
import com.project.rankers.databinding.ActivityRecordBinding
import com.project.rankers.viewmodels.RecordViewModel
import com.project.rankers.views.fragment.MultiFragment
import com.project.rankers.views.fragment.SingleFragment
import java.util.*

class RecordActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var recordBinding: ActivityRecordBinding
    private val viewModel = RecordViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        recordBinding = DataBindingUtil.setContentView(this, R.layout.activity_record)
        recordBinding.setVariable(BR.recordModel, viewModel)
        recordBinding.setVariable(BR.recordActivity, this)

        val mToolbar = recordBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        recordBinding.viewpager.adapter = pagerAdapter(supportFragmentManager)
        recordBinding.viewpager.currentItem = 0
        recordBinding.viewpager.offscreenPageLimit = 2
        recordBinding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(recordBinding.tabLayout))
        recordBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                recordBinding.viewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class pagerAdapter internal constructor(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        private val ARG_PAGE = "page"

        override fun getCount(): Int {
            return 2
        }

        @SuppressLint("Assert")
        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> {
                    fragment = SingleFragment()
                    return fragment
                }
                1 -> {
                    fragment = MultiFragment()
                    return fragment
                }
                else -> {
                }
            }
            val args = Bundle()
            args.putInt(ARG_PAGE, position)
            assert(false)
            fragment!!.arguments = args
            return fragment
        }
    }
}