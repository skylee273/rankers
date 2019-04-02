package com.project.rankers.ui.leagueResult

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.databinding.ActivityLeagueResultBinding
import com.project.rankers.databinding.ItemLeagueResultViewBinding
import com.project.rankers.ui.base.BaseActivity
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

    @SuppressLint("SetTextI18n")
    override fun onScore(mBinding: ItemLeagueResultViewBinding, type: Int) {
        MaterialDialog(this).show {
            title(text = "점수입력")
            customView(R.layout.dialog_league, scrollable = true)
            positiveButton(text = "확인") { dialog ->
                val winScore : EditText = dialog.getCustomView().findViewById(R.id.win_score)
                val loseScore : EditText = dialog.getCustomView().findViewById(R.id.lose_score)
                when(type){
                    1 -> {
                        mBinding.textScore1.text = winScore.text.toString() + " : " + loseScore.text.toString()
                        mBinding.textScore4.text = loseScore.text.toString() + " : " + winScore.text.toString()
                    }
                    2 -> {
                        mBinding.textScore2.text = winScore.text.toString() + " : " + loseScore.text.toString()
                        mBinding.textScore7.text = loseScore.text.toString() + " : " + winScore.text.toString()
                    }
                    3 -> {
                        mBinding.textScore3.text = winScore.text.toString() + " : " + loseScore.text.toString()
                        mBinding.textScore10.text = loseScore.text.toString() + " : " + winScore.text.toString()
                    }
                    5 -> {
                        mBinding.textScore5.text = winScore.text.toString() + " : " + loseScore.text.toString()
                        mBinding.textScore8.text = loseScore.text.toString() + " : " + winScore.text.toString()
                    }
                    6 -> {
                        mBinding.textScore6.text = winScore.text.toString() + " : " + loseScore.text.toString()
                        mBinding.textScore11.text = loseScore.text.toString() + " : " + winScore.text.toString()
                    }
                    9 -> {
                        mBinding.textScore9.text = winScore.text.toString() + " : " + loseScore.text.toString()
                        mBinding.textScore12.text = loseScore.text.toString() + " : " + winScore.text.toString()
                    }
                }
            }
            negativeButton(text = "취소")
        }
    }
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