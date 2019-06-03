package com.project.rankers.ui.main.message

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.remote.response.BoardRepo
import com.project.rankers.databinding.FragmentMsgBinding
import com.project.rankers.ui.base.BaseFragment
import com.project.rankers.ui.board.BoardActivity
import com.project.rankers.ui.reply.ReplyActivity
import java.util.*
import javax.inject.Inject

class MessageFragment : BaseFragment<FragmentMsgBinding, MessageViewModel>(), MessageNavigator, MessageAdapter.MessageAdapterListener {


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(context)
    private var messageViewModel: MessageViewModel? = null
    private var messageAdapter: MessageAdapter? = null

    private lateinit var msgBinding: FragmentMsgBinding

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_msg
    }

    override fun getViewModel(): MessageViewModel {
        messageViewModel = ViewModelProviders.of(this, factory).get(MessageViewModel::class.java)
        return messageViewModel as MessageViewModel
    }

    override fun handleError(throwable: Throwable) {
        displayLog("MessageFragment", throwable.toString())
    }


    override fun updateBoard(boardItem: List<BoardRepo.Board>) {
        messageAdapter!!.clearItems()
        messageAdapter!!.addItems(boardItem)
    }
    override fun onRetryClick() {
        messageViewModel!!.fetchCompetitions()
    }

    override fun openBoardActivity() {
        val nextIntent = Intent(context, BoardActivity::class.java)
        startActivity(nextIntent)
    }

    override fun nextBoardActivity(item : BoardRepo.Board) {
        val intent = Intent(context, ReplyActivity::class.java)
        val array = arrayOf(item.boardTitle,item.boardName, item.boardDate, item.boardViewCnt, item.boardReplyCnt, item.boardText, item.boardID)
        intent.putExtra("reply", array)
       startActivity(intent)
    }

    override fun onItemClick(item: BoardRepo.Board) {
        messageViewModel!!.updateBoards(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageViewModel!!.navigator = this
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        msgBinding = this.viewDataBinding!!
        msgBinding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                val text = msgBinding.editSearch.text.toString()
                        .toLowerCase(Locale.getDefault())
                messageAdapter!!.filter(text)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUp()
        refreshView()
    }

    private fun refreshView() {
        messageViewModel!!.fetchCompetitions()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        if (msgBinding.recycler.layoutManager != null) {
            msgBinding.recycler.layoutManager = null
        }
        messageAdapter = MessageAdapter(ArrayList<BoardRepo.Board>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        msgBinding.recycler.layoutManager = LinearLayoutManager(context)
        msgBinding.recycler.itemAnimator = DefaultItemAnimator()
        msgBinding.recycler.setHasFixedSize(true)
        msgBinding.recycler.adapter = messageAdapter
        messageAdapter!!.setListener(this)

    }


}