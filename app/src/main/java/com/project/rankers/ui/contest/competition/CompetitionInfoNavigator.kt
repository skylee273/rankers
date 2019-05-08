package com.project.rankers.ui.contest.competition

import com.project.rankers.data.remote.response.ContestResponse

interface CompetitionInfoNavigator {
    fun handleError(throwable: Throwable)
    fun updateContest(contest: List<ContestResponse.Repo>)
}