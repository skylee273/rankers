package com.project.rankers.ui.main

import android.os.Bundle
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.project.rankers.R
import com.project.rankers.databinding.ActivityMainBinding
import com.project.rankers.adapter.SectionsPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.adapter.model.SectionsPagerModel
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() , ViewPager.OnPageChangeListener, MainNavigator {

    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mMainViewModel : MainViewModel? = null
    private lateinit var mainBinding: ActivityMainBinding
    private var mSectionsPagerAdapter : SectionsPagerAdapter? = null
    private var sectionsPagerModel : SectionsPagerModel? = null
    private lateinit var prevBottomNavigation : MenuItem

    override fun handleError(throwable: Throwable) {
        //
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MainViewModel {
        mMainViewModel = ViewModelProviders.of(this,factory ).get(MainViewModel::class.java)
        return mMainViewModel as MainViewModel
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = getViewDataBinding()
        mMainViewModel!!.navigator = this

        val mToolbar = mainBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)


        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        mainBinding.viewPager.adapter = mSectionsPagerAdapter
        mainBinding.viewPager.addOnPageChangeListener(this)
        mainBinding.viewPager.currentItem = 0
        prevBottomNavigation = mainBinding.bottomNavigation.menu.getItem(0)
        mainBinding.viewPager.offscreenPageLimit = 3

        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_contest -> {
                    mainBinding.viewPager.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.tab_club -> {
                    mainBinding.viewPager.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.tab_message -> {
                    mainBinding.viewPager.currentItem = 2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.tab_profile -> {
                    mainBinding.viewPager.currentItem = 3
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        setSectionPagerModel(mSectionsPagerAdapter!!)
        loadSectionPagerItem()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainBinding.viewPager.removeOnPageChangeListener(this)
    }
    override fun onPageScrollStateChanged(p0: Int) {

    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

    }

    override fun onPageSelected(p0: Int) {
        prevBottomNavigation.isChecked = false
        prevBottomNavigation = mainBinding.bottomNavigation.menu.getItem(p0)
        prevBottomNavigation.isChecked = true
    }

    private fun updatePager(){
        mSectionsPagerAdapter!!.notifyDataSetChanged()
    }
    private fun setSectionPagerModel(sectionsPagerModel: SectionsPagerModel){
        this.sectionsPagerModel = sectionsPagerModel
    }
    private fun loadSectionPagerItem(){
        sectionsPagerModel!!.setListItem(0)
        sectionsPagerModel!!.setListItem(1)
        sectionsPagerModel!!.setListItem(2)
        sectionsPagerModel!!.setListItem(3)
        updatePager()
    }

}


