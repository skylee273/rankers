package com.project.rankers.ui.competition

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import java.util.*
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.ActivityCompetitionInfoBinding
import javax.inject.Inject
import kotlin.collections.ArrayList


class CompetitionInfoActivity : BaseActivity<ActivityCompetitionInfoBinding, CompetitionInfoViewModel>(), CompetitionInfoNavigator, CompetitionAdapter.CompetitionAdapterListener {


    @Inject
    internal var competitionAdapter: CompetitionAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    private lateinit var competitionInfoBinding: ActivityCompetitionInfoBinding
    private var competitionInfoViewModel: CompetitionInfoViewModel? = null

    override fun onRetryClick() {
        competitionInfoViewModel!!.fetchCompetitions()
    }

    override fun handleError(throwable: Throwable) {
        displayLog("CompetitionInfoActivity", throwable.toString())
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_competition_info
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): CompetitionInfoViewModel {
        competitionInfoViewModel = ViewModelProviders.of(this, factory).get(CompetitionInfoViewModel::class.java)
        return competitionInfoViewModel as CompetitionInfoViewModel
    }

    override fun updateContest(contest: List<ContestResponse.Repo>) {
        competitionAdapter!!.addItems(contest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        competitionInfoBinding = getViewDataBinding()
        competitionInfoViewModel!!.navigator = this

        setUp()

    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = competitionInfoBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        competitionAdapter = CompetitionAdapter(ArrayList<ContestResponse.Repo>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        competitionInfoBinding.recycler.layoutManager = mLayoutManager
        competitionInfoBinding.recycler.itemAnimator = DefaultItemAnimator()
        competitionInfoBinding.recycler.setHasFixedSize(true)
        competitionInfoBinding.recycler.adapter = competitionAdapter
        competitionAdapter!!.setListener(this)
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