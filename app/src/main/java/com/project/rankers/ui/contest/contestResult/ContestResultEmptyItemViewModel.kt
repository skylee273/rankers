package com.project.rankers.ui.contest.contestResult

class ContestResultEmptyItemViewModel(private val mListener: ContestResultEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface ContestResultEmptyItemViewModelListener {
        fun onRetryClick()
    }
}