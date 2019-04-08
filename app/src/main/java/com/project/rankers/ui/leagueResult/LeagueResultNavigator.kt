package com.project.rankers.ui.leagueResult

import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.data.remote.response.GroupResponse

interface LeagueResultNavigator {
    fun handleError(throwable: Throwable)
    fun updateGroup(groupItem: List<GroupResponse.Group>)
    fun uploadGroup()
}