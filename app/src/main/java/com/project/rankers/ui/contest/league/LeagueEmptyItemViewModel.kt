package com.project.rankers.ui.contest.league

class LeagueEmptyItemViewModel(private val mListener: LeagueEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface LeagueEmptyItemViewModelListener {
        fun onRetryClick()
    }
}