package com.project.rankers.ui.reply

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.ReplyRepo

class ReplyItemViewModel(items: ReplyRepo.Reply, private val mListener: ReplyItemViewModelListener) {

    val name : ObservableField<String>
    val text : ObservableField<String>

    init {
        name = ObservableField(items.replyName!!)
        text = ObservableField(items.replyText!!)

    }
    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  ReplyItemViewModelListener{
        fun onItemClick()
    }
}