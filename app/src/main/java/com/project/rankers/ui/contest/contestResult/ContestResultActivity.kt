package com.project.rankers.ui.contest.contestResult

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.ActivityContestResultBinding
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.ui.contest.operator.OperatorAdapter
import java.util.*
import javax.inject.Inject

class ContestResultActivity : BaseActivity<ActivityContestResultBinding, ContestResultViewModel>(), ContestResultNavigator, ContestResultAdapter.ContestResultAdapterListener{

    @Inject
    internal var contestResultAdapter: ContestResultAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    lateinit var activityContestResultBinding: ActivityContestResultBinding
    private var contestViewModel : ContestResultViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_contest_result
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel    }

    override fun getViewModel(): ContestResultViewModel {
        contestViewModel = ViewModelProviders.of(this, factory).get(ContestResultViewModel::class.java)
        return contestViewModel as ContestResultViewModel
    }
    override fun handleError(throwable: Throwable) {
        displayLog("ContestResultActivity", throwable.toString())
    }

    override fun updateContest(contest: List<ContestResponse.Repo>) {
        contestResultAdapter!!.addItems(contest)
    }

    override fun onRetryClick() {
        contestViewModel!!.fetchCompetitions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContestResultBinding = getViewDataBinding()
        contestViewModel!!.navigator = this
        setUp()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {

        val mToolbar = activityContestResultBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        contestResultAdapter = ContestResultAdapter(ArrayList<ContestResponse.Repo>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityContestResultBinding.recycler.layoutManager = mLayoutManager
        activityContestResultBinding.recycler.itemAnimator = DefaultItemAnimator()
        activityContestResultBinding.recycler.setHasFixedSize(true)
        activityContestResultBinding.recycler.adapter = contestResultAdapter
        contestResultAdapter!!.setListener(this)
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