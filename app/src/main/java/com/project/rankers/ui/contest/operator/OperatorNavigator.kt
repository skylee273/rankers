package com.project.rankers.ui.contest.operator

import com.project.rankers.data.remote.response.ContestResponse

interface OperatorNavigator {
    fun handleError(throwable: Throwable)
    fun updateContest(contest: List<ContestResponse.Repo>)
}