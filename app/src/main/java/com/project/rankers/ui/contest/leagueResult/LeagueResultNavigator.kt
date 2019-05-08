package com.project.rankers.ui.contest.leagueResult

import com.project.rankers.data.remote.response.GroupResponse

interface LeagueResultNavigator {
    fun handleError(throwable: Throwable)
    fun updateGroup(groupItem: List<GroupResponse.Group>)
    fun uploadGroup()
    fun showDialog(title : String, message : String)
}