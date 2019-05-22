package com.project.rankers.ui.board

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.ActivityBoardBinding
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class BoardActivity : BaseActivity<ActivityBoardBinding, BoardViewModel>(), BoardNavigator {


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private lateinit var boardBinding: ActivityBoardBinding
    private var boardViewModel : BoardViewModel? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_board
    }
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }
    override fun getViewModel(): BoardViewModel {
        boardViewModel = ViewModelProviders.of(this, factory).get(BoardViewModel::class.java)
        return boardViewModel as BoardViewModel
    }
    override fun successDialog(title: String, message: String) {
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

    override fun showDialog(title: String, message: String) {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = title)
                message(text =  message)
                positiveButton(text = "확인")
            }
        }
    }
    override fun handleError(throwable: Throwable) {
        Log.e("BoardActivity",throwable.message)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boardBinding = getViewDataBinding()
        boardViewModel!!.navigator = this

        setUp()
    }


    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = boardBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
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