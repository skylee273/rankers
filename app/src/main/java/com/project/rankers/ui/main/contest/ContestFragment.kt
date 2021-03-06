package com.project.rankers.ui.main.contest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.FragmentContestBinding
import com.project.rankers.ui.base.BaseFragment
import com.project.rankers.ui.contest.competition.CompetitionInfoActivity
import com.project.rankers.ui.contest.contestRegister.ContestRegisterActivity
import com.project.rankers.ui.contest.contestResult.ContestResultActivity
import com.project.rankers.ui.contest.modify.ContestModifyActivity
import com.project.rankers.ui.contest.operator.OperatorActivity
import javax.inject.Inject


class ContestFragment : BaseFragment<FragmentContestBinding, ContestViewModel>(), ContestNavigator, ContestAdapter.ContestAdapterListener {


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(context)
    private var contestViewModel: ContestViewModel? = null
    private var contestAdapter: ContestAdapter? = null

    private lateinit var contestBinding: FragmentContestBinding

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_contest
    }

    override fun getViewModel(): ContestViewModel {
        contestViewModel = ViewModelProviders.of(this, factory).get(ContestViewModel::class.java)
        return contestViewModel as ContestViewModel
    }

    override fun handleError(throwable: Throwable) {
        displayLog("ContestFragment", throwable.toString())
    }

    override fun updateContest(contest: List<ContestResponse.Repo>) {
        contestAdapter!!.clearItems()
        contestAdapter!!.addItems(contest)
    }

    override fun openContestRegister() {
        val nextIntent = Intent(context, ContestRegisterActivity::class.java)
        startActivity(nextIntent)
    }

    override fun openCompetitionInfo() {
        val nextIntent = Intent(context, CompetitionInfoActivity::class.java)
        startActivity(nextIntent)
    }

    override fun openOperator() {
        val nextIntent = Intent(context, OperatorActivity::class.java)
        startActivity(nextIntent)
    }

    override fun openContestResult() {
        val nextIntent = Intent(context, ContestResultActivity::class.java)
        startActivity(nextIntent)
    }

    override fun openModifyActivity() {
        val nextIntent = Intent(context, ContestModifyActivity::class.java)
        startActivity(nextIntent)
    }


    override fun onRetryClick() {
        contestViewModel!!.fetchCompetitions()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contestViewModel!!.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contestBinding = this.viewDataBinding!!

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUp()
        refreshView()
    }

    fun refreshView() {
        contestViewModel!!.fetchCompetitions()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        if (contestBinding.recycler.layoutManager != null) {
            contestBinding.recycler.layoutManager = null
        }
        contestAdapter = ContestAdapter(ArrayList<ContestResponse.Repo>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        contestBinding.recycler.layoutManager = LinearLayoutManager(context)
        contestBinding.recycler.itemAnimator = DefaultItemAnimator()
        contestBinding.recycler.setHasFixedSize(true)
        contestBinding.recycler.adapter = contestAdapter
        contestAdapter!!.setListener(this)

    }


}