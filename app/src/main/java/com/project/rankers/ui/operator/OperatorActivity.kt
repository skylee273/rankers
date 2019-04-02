package com.project.rankers.ui.operator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.databinding.ActivityOperatorBinding
import java.util.*
import javax.inject.Inject

class OperatorActivity : BaseActivity<ActivityOperatorBinding, OperatorViewModel>(), OperatorNavigator, OperatorAdapter.OperatorAdapterListener{

    @Inject
    internal var operatorAdapter: OperatorAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    lateinit var operationBinding: ActivityOperatorBinding
    private var operatorViewModel : OperatorViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_operator
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel    }

    override fun getViewModel(): OperatorViewModel {
        operatorViewModel = ViewModelProviders.of(this, factory).get(OperatorViewModel::class.java)
        return operatorViewModel as OperatorViewModel
    }
    override fun handleError(throwable: Throwable) {
        displayLog("OperatorActivity", throwable.toString())
    }

    override fun updateContest(contest: List<ContestResponse.Repo>) {
        operatorAdapter!!.addItems(contest)
    }

    override fun onRetryClick() {
        operatorViewModel!!.fetchCompetitions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        operationBinding = getViewDataBinding()
        operatorViewModel!!.navigator = this

        setUp()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {

        val mToolbar = operationBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        operatorAdapter = OperatorAdapter(ArrayList<ContestResponse.Repo>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        operationBinding.recycler.layoutManager = mLayoutManager
        operationBinding.recycler.itemAnimator = DefaultItemAnimator()
        operationBinding.recycler.setHasFixedSize(true)
        operationBinding.recycler.adapter = operatorAdapter
        operatorAdapter!!.setListener(this)
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