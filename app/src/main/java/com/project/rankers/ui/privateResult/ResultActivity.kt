package com.project.rankers.ui.privateResult

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
import com.project.rankers.databinding.ActivityResultBinding
import com.project.rankers.viewmodels.ResultViewModel
import com.project.rankers.ui.privateResult.fragment.ResultMultiFragment
import com.project.rankers.ui.privateResult.fragment.ResultSingleFragment
import java.util.*

class ResultActivity : AppCompatActivity() {

    lateinit var mContext: Context
    private lateinit var resultBinding: ActivityResultBinding
    private val viewModel = ResultViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        resultBinding = DataBindingUtil.setContentView(this, R.layout.activity_result)
        resultBinding.setVariable(BR.resultModel, viewModel)
        resultBinding.setVariable(BR.resultActivity, this)

        val mToolbar = resultBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        resultBinding.viewpager.adapter = pagerAdapter(supportFragmentManager)
        resultBinding.viewpager.currentItem = 0
        resultBinding.viewpager.offscreenPageLimit = 2
        resultBinding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(resultBinding.tabLayout2))
        resultBinding.tabLayout2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                resultBinding.viewpager.currentItem = tab.position
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
                    fragment = ResultSingleFragment()
                    return fragment
                }
                1 -> {
                    fragment = ResultMultiFragment()
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