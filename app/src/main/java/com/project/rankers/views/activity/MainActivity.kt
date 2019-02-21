package com.project.rankers.views.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.security.MessageDigest
import android.content.pm.PackageManager
import androidx.databinding.DataBindingUtil
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.ViewPager
import android.view.MenuItem
import com.project.rankers.R
import com.project.rankers.databinding.ActivityMainBinding
import com.kakao.util.helper.Utility.getPackageInfo
import com.project.rankers.adapter.SectionsPagerAdapter
import java.security.NoSuchAlgorithmException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.rankers.adapter.model.SectionsPagerModel


class MainActivity : AppCompatActivity() , ViewPager.OnPageChangeListener {

    private lateinit var mainBinding: ActivityMainBinding
    var mContext : Context? = null
    var mSectionsPagerAdapter : SectionsPagerAdapter? = null
    var sectionsPagerModel : SectionsPagerModel? = null
    lateinit var prevBottomNavigation : MenuItem

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //Log.d("MAIN", getKeyHash(mContext as MainActivity))
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

    /*
    @RequiresApi(Build.VERSION_CODES.O)
    fun getKeyHash(context: Context) : String? {
        val packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null
        for (signature in packageInfo.signatures){
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP)
            }catch (e : NoSuchAlgorithmException){
                Log.d("TAG", e.toString())
            }
        }
        return null
    }
    */
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


