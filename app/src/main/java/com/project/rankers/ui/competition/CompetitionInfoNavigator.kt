package com.project.rankers.ui.competition

import com.project.rankers.retrofit.items.ContestItem

interface CompetitionInfoNavigator {
    fun showDialog(title : String, message : String)
    fun handleError(throwable: Throwable)
    fun updateCompetition(contestItem : List<ContestItem>)
}