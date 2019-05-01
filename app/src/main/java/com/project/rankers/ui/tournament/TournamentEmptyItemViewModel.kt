package com.project.rankers.ui.tournament

class TournamentEmptyItemViewModel(private val mListener: TournamentEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface TournamentEmptyItemViewModelListener {
        fun onRetryClick()
    }
}