package com.project.rankers.ui.contest.operation.dashboard

interface DashBoardNavigator {
    fun handleError(throwable: Throwable)
    fun showDialog(title : String, message : String)
    fun openTournamentActivity()
    fun openLeagueActivity()
}