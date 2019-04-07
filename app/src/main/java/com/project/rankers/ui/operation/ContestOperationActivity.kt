package com.project.rankers.ui.operation

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.tabs.TabLayout
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.databinding.ActivityContestOperationBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject

class ContestOperationActivity : BaseActivity<ActivityContestOperationBinding, ContestOperationViewModel>(), ContestOperationNavigator, HasSupportFragmentInjector {

    @Inject
    internal var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    @Inject
    internal var contestOperationPagerAdapter : ContestOperationPagerAdapter? = null

    private var contestOperationViewModel : ContestOperationViewModel? = null
    private lateinit var activityContestOperationBinding: ActivityContestOperationBinding

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_contest_operation
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ContestOperationViewModel {
        contestOperationViewModel = ViewModelProviders.of(this, factory).get(ContestOperationViewModel::class.java)
        return contestOperationViewModel as ContestOperationViewModel
    }

    override fun showDialog(title: String, message: String) {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = title)
                message(text =  message)
                positiveButton(text = "확인")
            }
        }
    }

    override fun handleError(throwable: Throwable) {
        Log.e("form",throwable.message)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContestOperationBinding = getViewDataBinding()
        contestOperationViewModel!!.navigator = this
        setUp()

    }

    private fun setUp(){
        val mToolbar = activityContestOperationBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        contestOperationPagerAdapter = ContestOperationPagerAdapter(supportFragmentManager)

        contestOperationPagerAdapter!!.count = 3
        activityContestOperationBinding.viewpager.adapter = contestOperationPagerAdapter

        contestOperationPagerAdapter!!.setBundle(intent)

        activityContestOperationBinding.tabLayout.addTab(activityContestOperationBinding.tabLayout.newTab().setText("대진표작성"))
        activityContestOperationBinding.tabLayout.addTab(activityContestOperationBinding.tabLayout.newTab().setText("대회결과표"))
        activityContestOperationBinding.tabLayout.addTab(activityContestOperationBinding.tabLayout.newTab().setText("매니저등록"))

        activityContestOperationBinding.viewpager.offscreenPageLimit = activityContestOperationBinding.tabLayout.tabCount
        activityContestOperationBinding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(activityContestOperationBinding.tabLayout))
        activityContestOperationBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                activityContestOperationBinding.viewpager.currentItem = tab.position
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