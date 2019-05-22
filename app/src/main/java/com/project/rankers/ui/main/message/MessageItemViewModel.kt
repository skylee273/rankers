package com.project.rankers.ui.main.message

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.BoardRepo

class MessageItemViewModel(items: BoardRepo.Board, private val mListener: MessageItemViewListener) {

    val title : ObservableField<String>
    val text : ObservableField<String>
    val name : ObservableField<String>
    val date : ObservableField<String>
    val replyCnt : ObservableField<String>
    val viewCnt: ObservableField<String>

    init {
        title = ObservableField(items.boardTitle!!)
        text = ObservableField(items.boardText!!)
        name = ObservableField(items.boardName!!)
        date = ObservableField(items.boardDate!!)
        replyCnt = ObservableField(items.boardReplyCnt!!)
        viewCnt = ObservableField("조회 " + items.boardViewCnt!!)

    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  MessageItemViewListener{
        fun onItemClick()
    }
}