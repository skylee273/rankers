package com.project.rankers.ui.contest.leagueResult

class LeagueResultEmptyItemViewModel(private val mListener: LeagueResultEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface LeagueResultEmptyItemViewModelListener {
        fun onRetryClick()
    }
}