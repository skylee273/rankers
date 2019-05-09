package com.project.rankers.ui.contest.contestResultSub

class ContestResultSubEmptyItemViewModel(private val mListener: ContestResultEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface ContestResultEmptyItemViewModelListener {
        fun onRetryClick()
    }
}