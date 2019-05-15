package com.project.rankers.ui.contest.operation.dashboard

class DashBoardItemEmptyViewModel (private val mListener: DashBoardEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface DashBoardEmptyItemViewModelListener {
        fun onRetryClick()
    }
}