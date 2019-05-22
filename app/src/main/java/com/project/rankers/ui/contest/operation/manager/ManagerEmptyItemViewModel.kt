package com.project.rankers.ui.contest.operation.manager

class ManagerEmptyItemViewModel(private val mListener: ManagerEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface ManagerEmptyItemViewModelListener {
        fun onRetryClick()
    }
}