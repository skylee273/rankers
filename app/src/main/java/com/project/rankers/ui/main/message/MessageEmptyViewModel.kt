package com.project.rankers.ui.main.message

class MessageEmptyViewModel(private val mListener: MessageEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface MessageEmptyItemViewModelListener {
        fun onRetryClick()
    }
}