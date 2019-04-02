package com.project.rankers.ui.personal.writing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem

import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.databinding.ActivityWritingPersonalRecordBinding
import com.project.rankers.ui.fragment.MultiFragment
import com.project.rankers.ui.fragment.SingleFragment
import java.util.*
import javax.inject.Inject

class WritingPersonalRecordActivity : BaseActivity<ActivityWritingPersonalRecordBinding, WritingPersonalRecordViewModel>(), WritingNavigator {

    @Inject
    internal var factory : ViewModelProviderFactory? = null
    private lateinit var writingPersonalRecordBinding: ActivityWritingPersonalRecordBinding
    private var mWritingPersonalRecordViewModel : WritingPersonalRecordViewModel? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_writing_personal_record
    }

    override fun getBindingVariable(): Int {
       return BR.viewModel
    }

    override fun getViewModel(): WritingPersonalRecordViewModel {
        mWritingPersonalRecordViewModel = ViewModelProviders.of(this, factory).get(WritingPersonalRecordViewModel::class.java)
        return mWritingPersonalRecordViewModel as WritingPersonalRecordViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        writingPersonalRecordBinding = getViewDataBinding()
        mWritingPersonalRecordViewModel!!.navigator = this

        // toolbar
        val mToolbar = writingPersonalRecordBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        // viewpager
        writingPersonalRecordBinding.viewpager.adapter = PageAdapter(supportFragmentManager)
        writingPersonalRecordBinding.viewpager.currentItem = 0
        writingPersonalRecordBinding.viewpager.offscreenPageLimit = 2
        writingPersonalRecordBinding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(writingPersonalRecordBinding.tabLayout))
        writingPersonalRecordBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                writingPersonalRecordBinding.viewpager.currentItem = tab.position
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

    inner class PageAdapter internal constructor(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        private val page = "page"
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
            args.putInt(page, position)
            assert(false)
            fragment!!.arguments = args
            return fragment
        }
    }
}