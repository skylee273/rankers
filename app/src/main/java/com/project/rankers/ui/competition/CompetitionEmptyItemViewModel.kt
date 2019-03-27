package com.project.rankers.ui.competition

class CompetitionEmptyItemViewModel(private val mListener: CompetitionEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface CompetitionEmptyItemViewModelListener {
        fun onRetryClick()
    }
}