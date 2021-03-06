package com.project.rankers.ui.main.contest

import com.project.rankers.data.remote.response.ContestResponse

interface ContestNavigator {
    fun handleError(throwable: Throwable)
    fun updateContest(contest: List<ContestResponse.Repo>)
    fun openContestRegister()
    fun openContestResult()
    fun openCompetitionInfo()
    fun openOperator()
    fun openModifyActivity()
}