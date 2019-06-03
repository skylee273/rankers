package com.project.rankers.ui.contest.modify

import com.project.rankers.data.remote.response.ContestResponse

interface ContestModifyNavigator {
    fun handleError(throwable: Throwable)
    fun updateContest(contest: List<ContestResponse.Repo>)
    fun successDialog(title : String, text : String)
}