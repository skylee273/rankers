package com.project.rankers.ui.contest.contestResultSub

interface ContestResultSubNavigator {
    fun handleError(throwable: Throwable)
    fun updateContest(depart: List<String>)
}