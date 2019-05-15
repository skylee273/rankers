package com.project.rankers.ui.contest.contestResultLeague

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.databinding.ActivityContestResultLeagueBinding
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class ContestResultLeagueActivity : BaseActivity<ActivityContestResultLeagueBinding, ContestResultLeagueViewModel>() , ContestResultLeagueNavigator, ContestResultLeagueAdapter.GroupAdapterListener{

    @Inject
    internal var contestResultAdapter: ContestResultLeagueAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    private lateinit var activityContestResultLeagueBinding : ActivityContestResultLeagueBinding
    private var contestResultViewModel: ContestResultLeagueViewModel? = null


    override fun showDialog(title: String, message: String) {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = title)
                message(text =  message)
                positiveButton(text = "확인"){
                    finish()
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_contest_result_league
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ContestResultLeagueViewModel {
        contestResultViewModel = ViewModelProviders.of(this, factory).get(ContestResultLeagueViewModel::class.java)
        return contestResultViewModel as ContestResultLeagueViewModel
    }
    override fun handleError(throwable: Throwable) {
        displayLog("ContestResultLeagueActivity", throwable.toString())
    }

    override fun updateGroup(groupItem: List<GroupResponse.Group>) {
        contestResultAdapter!!.addItems(groupItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContestResultLeagueBinding = getViewDataBinding()
        contestResultViewModel!!.navigator = this

        setUp()

    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = activityContestResultLeagueBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val intent = intent
        val id = intent.extras.getString("CONTEST_ID")
        val depart = intent.extras.getString("CONTEST_DEPART")

        contestResultViewModel!!.setContestInfo(id,depart)

        contestResultAdapter = ContestResultLeagueAdapter(ArrayList<GroupResponse.Group>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityContestResultLeagueBinding.recycler.layoutManager = mLayoutManager
        activityContestResultLeagueBinding.recycler.itemAnimator = DefaultItemAnimator()
        activityContestResultLeagueBinding.recycler.setHasFixedSize(true)
        activityContestResultLeagueBinding.recycler.adapter = contestResultAdapter
        contestResultAdapter!!.setListener(this)
    }
}