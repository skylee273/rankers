package com.project.rankers.ui.match

class MatchEmptyItemViewModel (private val mListener: MatchEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface MatchEmptyItemViewModelListener {
        fun onRetryClick()
    }
}