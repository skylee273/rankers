package com.project.rankers.ui.reply

class ReplyEmptyItemViewModel (private val mListener: ReplyEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface ReplyEmptyItemViewModelListener {
        fun onRetryClick()
    }
}