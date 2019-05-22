package com.project.rankers.ui.contest.operation.result

class ResultContestEmptyViewModel (private val mListener: ResultContestEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface ResultContestEmptyItemViewModelListener {
        fun onRetryClick()
    }
}