package com.project.rankers.ui.contest.contestResultTournament

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.project.rankers.MvvmApp
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.ActivityContestResultTournamentBinding
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.ui.contest.contestResultTournament.fragment.ContestResultTournamentFragment
import java.util.*
import javax.inject.Inject

class ContestResultTournamentActivity : BaseActivity<ActivityContestResultTournamentBinding, ContestResultTournamentViewModel>(), ContestResultTournamentNavigator {

    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var contestResultTournamentViewModel: ContestResultTournamentViewModel? = null
    lateinit var activityContestResultTournamentBinding: ActivityContestResultTournamentBinding
    private var contestID: String? = null
    private var contestDepart: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_contest_result_tournament
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ContestResultTournamentViewModel {
        contestResultTournamentViewModel = ViewModelProviders.of(this, factory).get(ContestResultTournamentViewModel::class.java)
        return contestResultTournamentViewModel as ContestResultTournamentViewModel
    }

    override fun handleError(throwable: Throwable) {
        displayLog("ContestResultTournamentActivity", throwable.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContestResultTournamentBinding = getViewDataBinding()
        contestResultTournamentViewModel!!.navigator = this
        setUp()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = activityContestResultTournamentBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val intent = intent
        contestID = intent.extras!!.getString("CONTEST_ID")
        contestDepart = intent.extras!!.getString("CONTEST_DEPART")

        initialiseTournamentFragment()
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

    private fun initialiseTournamentFragment() {
        var fragment: Fragment? = null
        fragment = ContestResultTournamentFragment.newInstance()
        fragment.arguments = getBundle()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.container, fragment, "brackets_home_fragment")
        transaction.commit()
        manager.executePendingTransactions()
    }

    private fun getBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString("CONTEST_ID", contestID)
        bundle.putString("CONTEST_DEPART", contestDepart)
        return bundle
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