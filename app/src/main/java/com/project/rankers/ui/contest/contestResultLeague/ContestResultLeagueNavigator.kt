package com.project.rankers.ui.contest.contestResultLeague

import com.project.rankers.data.remote.response.GroupResponse

interface ContestResultLeagueNavigator {
    fun handleError(throwable: Throwable)
    fun updateGroup(groupItem: List<GroupResponse.Group>)
    fun showDialog(title : String, message : String)
}