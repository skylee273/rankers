package com.project.rankers.ui.contest.competition

class CompetitionEmptyItemViewModel(private val mListener: CompetitionEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface CompetitionEmptyItemViewModelListener {
        fun onRetryClick()
    }
}