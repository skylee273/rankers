package com.project.rankers.ui.contest.contestResultSub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.ActivityContestResultSubBinding
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.ui.contest.contestResultLeague.ContestResultLeagueActivity
import com.project.rankers.ui.contest.contestResultTournament.ContestResultTournamentActivity
import java.util.*
import javax.inject.Inject

class ContestResultSubActivity : BaseActivity<ActivityContestResultSubBinding, ContestResultSubViewModel>(), ContestResultSubNavigator, ContestResultSubAdapter.ContestResultSubAdapterListener{

    @Inject
    internal var contestResultAdapter: ContestResultSubAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    lateinit var activityContestResultBinding: ActivityContestResultSubBinding
    private var contestViewModel : ContestResultSubViewModel? = null
    var contestID : String? = null
    var contestDepart : String? = null
    private val myItems = listOf("예선전", "토너먼트")

    override fun getLayoutId(): Int {
        return R.layout.activity_contest_result_sub
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel    }

    override fun getViewModel(): ContestResultSubViewModel {
        contestViewModel = ViewModelProviders.of(this, factory).get(ContestResultSubViewModel::class.java)
        return contestViewModel as ContestResultSubViewModel
    }
    override fun handleError(throwable: Throwable) {
        displayLog("ContestResultSubActivity", throwable.toString())
    }

    override fun updateContest(depart: List<String>) {
        contestResultAdapter!!.addItems(depart)
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

    override fun onItemClick(depart : String) {
        var p0 = 0
        MaterialDialog(this).show {
            title(text = "대진표작성")
            listItemsSingleChoice(items = myItems, waitForPositiveButton = false) { dialog, index, text ->
                dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                p0 = index
            }
            positiveButton(text = "확인"){
                when (p0) {
                    0 -> {
                        Log.d("대진표 번호", "예선")
                        val intent = Intent(mContext, ContestResultLeagueActivity::class.java)
                        intent.putExtra("CONTEST_DEPART", depart)
                        intent.putExtra("CONTEST_ID", contestID)
                        startActivity(intent)
                    }
                    1 -> {
                        Log.d("대진표 번호", "본선")
                        val intent = Intent(mContext, ContestResultTournamentActivity::class.java)
                        intent.putExtra("CONTEST_DEPART", depart)
                        intent.putExtra("CONTEST_ID", contestID)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {

        val mToolbar = activityContestResultBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)


        val intent = intent
        contestID     = intent.extras.getString("CONTEST_ID")
        contestDepart = intent.extras.getString("CONTEST_DEPART")
        contestViewModel!!.setContestInfo(contestID!!, contestDepart!!)

        contestResultAdapter = ContestResultSubAdapter(ArrayList<String>())
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