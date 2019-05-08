package com.project.rankers.ui.contest.contestResult

import com.project.rankers.data.remote.response.ContestResponse

interface ContestResultNavigator {
    fun handleError(throwable: Throwable)
    fun updateContest(contest: List<ContestResponse.Repo>)
}