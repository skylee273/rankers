package com.project.rankers.ui.operator

class OperatorEmptyItemViewModel(private val mListener: OperatorEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface OperatorEmptyItemViewModelListener {
        fun onRetryClick()
    }
}