package com.project.rankers.ui.contest.league

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.model.db.LeagueItem
import com.project.rankers.databinding.ActivityLeagueBinding
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LeagueActivity : BaseActivity<ActivityLeagueBinding, LeagueViewModel>(), LeagueNavigator, LeagueAdapter.LeagueAdapterListener {


    @Inject
    internal var leagueAdapter: LeagueAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private lateinit var leagueBinding: ActivityLeagueBinding
    private var mLayoutManager = LinearLayoutManager(this)
    private var leagueViewModel: LeagueViewModel? = null
    private var peopleItems = ArrayList<String>()

    override fun onRetryClick() {
        //
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_league
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): LeagueViewModel {
        leagueViewModel = ViewModelProviders.of(this, factory).get(LeagueViewModel::class.java)
        return leagueViewModel as LeagueViewModel
    }

    override fun handleError(throwable: Throwable) {
        displayLog("LeagueActivity", throwable.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun setGroupCount(count: Int, num: Int, peopleItem: ArrayList<String>) {
        this.peopleItems = peopleItem
        leagueBinding.textCount.text = "참여인원  총  " + count.toString() + "명"
        leagueBinding.textNumber.text = "$num"
    }

    override fun uploadLeague() {
        leagueViewModel!!.uploadGroup(leagueAdapter!!.getItems())
    }

    override fun showGroupNumber(num: Int) {
        leagueBinding.textNumber.text = "$num"
    }

    override fun showRecyclerView(items: ArrayList<LeagueItem>) {
        leagueAdapter!!.addItems(items)
    }

    override fun onAddPeopleDialog(position: Int, type: Int, playerName: String) {
        peopleItems.add(playerName)
        leagueAdapter!!.modifyItem(position, "", type)
    }

    override fun isNextActivity() {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = "예선 대진표")
                message(text = "대진표 등록이 완료되었습니다.")
                positiveButton(text = "확인") {
                    finish()
                }
            }
        }
    }

    override fun failDialog() {
        MaterialDialog(this).show {
            title(text = "선수")
            message(text = "선수인원이 없습니다.")
            positiveButton(text = "확인")
        }
    }

    override fun onShowPeopleDialog(position: Int, type: Int) {
        if (peopleItems.size > 0) {
            MaterialDialog(this).show {
                title(text = "선수명단")
                message(text = "선수를 1명 선택하세요")
                listItemsSingleChoice(items = peopleItems) { _, index, text ->
                    if(peopleItems.size == index){
                        peopleItems.removeAt(peopleItems.size-1)
                    }else{
                        peopleItems.removeAt(index)
                    }
                    leagueAdapter!!.modifyItem(position, text, type)
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        leagueBinding = getViewDataBinding()
        leagueViewModel!!.navigator = this

        setUP()
        val intent = intent
        val contestID = intent.extras.getString("CONTEST_ID")
        val contestDepart = intent.extras.getString("CONTEST_DEPART")
        leagueViewModel!!.setContestInfo(contestID, contestDepart)
    }

    @SuppressLint("WrongConstant")
    private fun setUP() {
        val mToolbar = leagueBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        leagueAdapter = LeagueAdapter(ArrayList<LeagueItem>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        leagueBinding.recycler.layoutManager = mLayoutManager
        leagueBinding.recycler.itemAnimator = DefaultItemAnimator()
        leagueBinding.recycler.setHasFixedSize(true)
        leagueBinding.recycler.adapter = leagueAdapter
        leagueAdapter!!.setListener(this)

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