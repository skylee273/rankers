package com.project.rankers.ui.contest.operator

class OperatorEmptyItemViewModel(private val mListener: OperatorEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface OperatorEmptyItemViewModelListener {
        fun onRetryClick()
    }
}