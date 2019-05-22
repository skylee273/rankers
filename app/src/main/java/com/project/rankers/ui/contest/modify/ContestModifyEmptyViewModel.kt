package com.project.rankers.ui.contest.modify

class ContestModifyEmptyViewModel(private val mListener: ModifyEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface ModifyEmptyItemViewModelListener {
        fun onRetryClick()
    }
}