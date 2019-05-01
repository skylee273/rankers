package com.project.rankers.ui.tournament

import android.os.Bundle
import android.util.DisplayMetrics

import com.project.rankers.R
import com.project.rankers.ui.tournament.Fragment.BracketsFragment

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.project.rankers.MvvmApp
import java.util.*

class TournamentResultActivity : AppCompatActivity() {


    private var bracketFragment: BracketsFragment? = null
    private var contestId : String? =null
    private var contestDepart : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_result)

        contestId = intent.extras!!.getString("CONTEST_ID")
        contestDepart = intent.extras!!.getString("CONTEST_DEPART")

        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        initialiseBracketsFragment()

    }

    private fun initialiseBracketsFragment() {
        var fragment: Fragment? = null
        fragment = BracketsFragment.newInstance()
        fragment.arguments = getBundle()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.container, fragment!!, "brackets_home_fragment")
        transaction.commit()
        manager.executePendingTransactions()
    }

    override fun onResume() {
        super.onResume()
        setScreenSize()

    }

    private fun setScreenSize() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        MvvmApp.instance!!.screenHeight = height
    }

    private fun getBundle() : Bundle{
        val bundle  =  Bundle()
        bundle.putString("CONTEST_ID",contestId)
        bundle.putString("CONTEST_DEPART",contestDepart)
        return bundle
    }

}