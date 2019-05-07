package com.project.rankers.ui.record

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.ActivityRecordBinding
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.ui.operation.ContestOperationPagerAdapter
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject

class RecordActivity : BaseActivity<ActivityRecordBinding, RecordViewModel>(), RecordNavigator, HasSupportFragmentInjector{

    @Inject
    internal var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    @Inject
    internal var recordPagerAdapter : RecordPagerAdapter? = null

    private var recordViewModel : RecordViewModel? = null
    private lateinit var activityRecordBinding: ActivityRecordBinding

    override fun getLayoutId(): Int {
        return R.layout.activity_record
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): RecordViewModel {
        recordViewModel = ViewModelProviders.of(this, factory).get(RecordViewModel::class.java)
        return recordViewModel as RecordViewModel
    }

    override fun handleError(throwable: Throwable) {
       displayLog("RecordActivity", throwable.toString())
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecordBinding = getViewDataBinding()
        recordViewModel!!.navigator = this
        setUp()

    }

    private fun setUp(){
        val mToolbar = activityRecordBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        recordPagerAdapter = RecordPagerAdapter(supportFragmentManager)

        recordPagerAdapter!!.count = 2
        activityRecordBinding.viewpager.adapter = recordPagerAdapter

        activityRecordBinding.tabLayout.addTab(activityRecordBinding.tabLayout.newTab().setText("단식"))
        activityRecordBinding.tabLayout.addTab(activityRecordBinding.tabLayout.newTab().setText("복식"))

        activityRecordBinding.viewpager.offscreenPageLimit = activityRecordBinding.tabLayout.tabCount
        activityRecordBinding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(activityRecordBinding.tabLayout))
        activityRecordBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                activityRecordBinding.viewpager.currentItem = tab.position
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
}