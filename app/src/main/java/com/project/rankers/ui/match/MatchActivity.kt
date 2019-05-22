package com.project.rankers.ui.match

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.remote.response.MatchRepo
import com.project.rankers.databinding.ActivityMatchBinding
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class MatchActivity : BaseActivity<ActivityMatchBinding, MatchViewModel>(), MatchNavigator, MatchAdapter.MatchAdapterListner {

    @Inject
    internal var matchAdapter: MatchAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    private lateinit var matchBinding: ActivityMatchBinding
    private var matchViewModel: MatchViewModel? = null


    override fun getLayoutId(): Int {
       return R.layout.activity_match
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MatchViewModel {
        matchViewModel = ViewModelProviders.of(this, factory).get(MatchViewModel::class.java)
        return matchViewModel as MatchViewModel
    }

    override fun handleError(throwable: Throwable) {
        displayLog("MatchActivity", throwable.toString())
    }

    override fun showMatchList(matchItem: List<MatchRepo.Match>) {
        matchAdapter!!.addItems(matchItem)
    }
    override fun isNextActivity() {
        //
    }

    override fun failDialog() {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = "개인기록")
                message(text = "개인기록을 불러오는데 실패하였습니다.")
                positiveButton { R.string.agree }
            }
        }
    }

    override fun onRetryClick() {
        //
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchBinding = getViewDataBinding()
        matchViewModel!!.navigator = this

        setUp()

    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = matchBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        matchAdapter = MatchAdapter(ArrayList<MatchRepo.Match>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL

        matchBinding.recycler.layoutManager = mLayoutManager
        matchBinding.recycler.itemAnimator = DefaultItemAnimator()
        matchBinding.recycler.setHasFixedSize(true)
        matchBinding.recycler.adapter = matchAdapter
        matchAdapter!!.setListener(this)

        matchBinding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                val text = matchBinding.editSearch.text.toString()
                        .toLowerCase(Locale.getDefault())
                matchAdapter!!.filter(text)
            }
        })
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