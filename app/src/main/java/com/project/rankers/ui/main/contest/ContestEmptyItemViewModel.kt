package com.project.rankers.ui.main.contest

class ContestEmptyItemViewModel(private val mListener: ContestEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface ContestEmptyItemViewModelListener {
        fun onRetryClick()
    }
}