package com.project.rankers.ui.leagueResult

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.model.db.LEAGUE_RESULT
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.data.remote.response.LeagueRepo
import com.project.rankers.databinding.ActivityLeagueResultBinding
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.ui.competition.CompetitionAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class LeagueResultActivity : BaseActivity<ActivityLeagueResultBinding, LeagueResultViewModel>() , LeagueResultNavigator, LeagueResultAdapter.GroupAdapterListener{


    @Inject
    internal var leagueResultAdapter: LeagueResultAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    private lateinit var activityLeagueResultBinding : ActivityLeagueResultBinding
    private var leagueResultViewModel: LeagueResultViewModel? = null

    override fun onRetryClick() {
        leagueResultViewModel!!.fetchGroups()
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_league_result
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): LeagueResultViewModel {
        leagueResultViewModel = ViewModelProviders.of(this, factory).get(LeagueResultViewModel::class.java)
        return leagueResultViewModel as LeagueResultViewModel
    }
    override fun handleError(throwable: Throwable) {
        displayLog("LeagueResultActivity", throwable.toString())
    }

    override fun updateGroup(groupItem: List<GroupResponse.Group>) {
        leagueResultAdapter!!.addItems(groupItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLeagueResultBinding = getViewDataBinding()
        leagueResultViewModel!!.navigator = this

        setUp()

        val intent = intent
        val id = intent.extras.getString("CONTEST_ID")
        val depart = intent.extras.getString("CONTEST_DEPART")

        leagueResultViewModel!!.setContestInfo(id,depart)
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = activityLeagueResultBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        leagueResultAdapter = LeagueResultAdapter(ArrayList<GroupResponse.Group>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityLeagueResultBinding.recycler.layoutManager = mLayoutManager
        activityLeagueResultBinding.recycler.itemAnimator = DefaultItemAnimator()
        activityLeagueResultBinding.recycler.setHasFixedSize(true)
        activityLeagueResultBinding.recycler.adapter = leagueResultAdapter
        leagueResultAdapter!!.setListener(this)
    }
}