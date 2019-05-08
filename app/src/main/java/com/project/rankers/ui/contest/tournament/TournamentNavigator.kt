package com.project.rankers.ui.contest.tournament

import com.project.rankers.data.model.db.Tournament

interface TournamentNavigator {
    fun handleError(throwable: Throwable)
    fun updateTournament(groupItem: List<Tournament>)
    fun uploadTournament()
    fun showDialog(title : String, message : String)
}