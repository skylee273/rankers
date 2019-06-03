package com.project.rankers.ui.reply

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.remote.response.ReplyRepo
import com.project.rankers.databinding.ActivityReplyBinding
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class ReplyActivity : BaseActivity<ActivityReplyBinding, ReplyViewModel>(), ReplyNavigator, ReplyAdapter.ReplyAdapterListner {



    @Inject
    internal var replyAdapter: ReplyAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    private lateinit var replyBinding: ActivityReplyBinding
    private var replyViewModel: ReplyViewModel? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_reply
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ReplyViewModel {
        replyViewModel = ViewModelProviders.of(this, factory).get(ReplyViewModel::class.java)
        return replyViewModel as ReplyViewModel
    }

    override fun handleError(throwable: Throwable) {
        displayLog("ReplyActivity", throwable.toString())
    }
    override fun showReplyList(replyItem: List<ReplyRepo.Reply>) {
        replyAdapter!!.addItems(replyItem)
    }

    override fun failDialog() {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = "자유게시판")
                message(text = "자유게시판 정보를 읽어오는데 실패하였습니다.")
                positiveButton { R.string.agree }
            }
        }
    }

    override fun showLengthDialog() {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = "자유게시판")
                message(text = "글자수는 80자가 최대 입니다.")
                positiveButton { R.string.agree }
            }
        }
    }



    override fun onRetryClick() {
        //
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replyBinding = getViewDataBinding()
        replyViewModel!!.navigator = this

        setUp()

    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = replyBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val intent = intent
        val replyArray = intent.extras.getStringArray("reply")!!
        if(replyArray.isNotEmpty()){
            replyViewModel!!.setBoard(replyArray[0],replyArray[1],replyArray[2],replyArray[3],replyArray[4],replyArray[5],replyArray[6])
        }

        replyAdapter = ReplyAdapter(ArrayList<ReplyRepo.Reply>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL

        replyBinding.recycler.layoutManager = mLayoutManager
        replyBinding.recycler.itemAnimator = DefaultItemAnimator()
        replyBinding.recycler.setHasFixedSize(true)
        replyBinding.recycler.adapter = replyAdapter
        replyAdapter!!.setListener(this)

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