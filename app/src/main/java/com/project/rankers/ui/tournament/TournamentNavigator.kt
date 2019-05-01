package com.project.rankers.ui.tournament

import com.project.rankers.data.model.db.Tournament
import com.project.rankers.data.remote.response.GroupResponse

interface TournamentNavigator {
    fun handleError(throwable: Throwable)
    fun updateTournament(groupItem: List<Tournament>)
    fun uploadTournament()
    fun showDialog(title : String, message : String)
}