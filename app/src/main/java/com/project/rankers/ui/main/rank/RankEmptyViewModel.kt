package com.project.rankers.ui.main.rank

class RankEmptyViewModel(private val mListener: RankEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface RankEmptyItemViewModelListener {
        fun onRetryClick()
    }
}