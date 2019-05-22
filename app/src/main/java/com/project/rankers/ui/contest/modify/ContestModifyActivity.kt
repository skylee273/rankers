package com.project.rankers.ui.contest.modify

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.ActivityModifyBinding
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class ContestModifyActivity : BaseActivity<ActivityModifyBinding, ContestModifyViewModel>(), ContestModifyNavigator, ContestModifyAdapter.ModifyAdapterListener{


    @Inject
    internal var modifyAdapter: ContestModifyAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    lateinit var modifyBinding: ActivityModifyBinding
    private var modifyViewModel : ContestModifyViewModel? = null
    private val myItems = listOf("대회수정", "대회삭제")

    override fun getLayoutId(): Int {
        return R.layout.activity_modify
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel    }

    override fun getViewModel(): ContestModifyViewModel {
        modifyViewModel = ViewModelProviders.of(this, factory).get(ContestModifyViewModel::class.java)
        return modifyViewModel as ContestModifyViewModel
    }
    override fun handleError(throwable: Throwable) {
        displayLog("OperatorActivity", throwable.toString())
    }

    override fun updateContest(contest: List<ContestResponse.Repo>) {
        modifyAdapter!!.addItems(contest)
    }

    override fun onRetryClick() {
        modifyViewModel!!.fetchCompetitions()
    }

    override fun onItemClick(item: ContestResponse.Repo) {
        var p0 = 0
        MaterialDialog(this).show {
            title(text = "대회수정")
            listItemsSingleChoice(items = myItems, waitForPositiveButton = false) { dialog, index, text ->
                dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                p0 = index
            }
            positiveButton(text = "확인"){
                when (p0) {
                    0 -> {
                        //val intent = Intent(mContext, ContestResultLeagueActivity::class.java)
                        //startActivity(intent)
                    }
                    1 -> {
                        modifyViewModel!!.deleteContest(item.id!!)
                    }
                }
            }
        }    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modifyBinding = getViewDataBinding()
        modifyViewModel!!.navigator = this

        setUp()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {

        val mToolbar = modifyBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        modifyAdapter = ContestModifyAdapter(ArrayList<ContestResponse.Repo>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        modifyBinding.recycler.layoutManager = mLayoutManager
        modifyBinding.recycler.itemAnimator = DefaultItemAnimator()
        modifyBinding.recycler.setHasFixedSize(true)
        modifyBinding.recycler.adapter = modifyAdapter
        modifyAdapter!!.setListener(this)
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