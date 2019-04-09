package com.project.rankers.ui.league

import android.annotation.SuppressLint
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
import com.project.rankers.databinding.ActivityLeagueBinding
import com.project.rankers.data.model.db.LeagueItem
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
    private var peopleItems = mutableListOf<String>()

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
    override fun setGroupCount(count: Int, num: Int, item : MutableList<String>) {
        this.peopleItems = item
        leagueBinding.textCount.text = "참여인원  총  " + count.toString() +  "명"
        leagueBinding.textNumber.text = "$num"
    }
    override fun uploadLeague() {
        leagueViewModel!!.uploadGroup(leagueAdapter!!.getItems())
    }
    override fun showGroupNumber(num: Int) {
        leagueBinding.textNumber.text = "$num"
    }
    override fun showRecyclerView(items : ArrayList<LeagueItem>) {
        leagueAdapter!!.addItems(items)
    }

    override fun isNextActivity() {
        MaterialDialog(this).show {
            title(text = "예선 대진표")
            message(text = "대진표 등록이 완료되었습니다.")
            positiveButton(text = "확인") {
                finish()
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
    override fun onShowPeopleDialog(position : Int, type : Int) {
        Log.d("onShowPeopleDialog","생성")

        var playerName : String? = null
        MaterialDialog(this).show {
            title(text = "선수명단")
            message(text = "최소 선수를 1 ~ 4명 선택하세요")
            listItemsSingleChoice(items = peopleItems, waitForPositiveButton = false) { dialog, index, text ->
                dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                playerName = text
            }
            positiveButton(text = "확인") {
                leagueAdapter!!.modifyItem(position, playerName!!, type )
            }
            negativeButton(text = "취소")
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
    private fun setUP(){
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