package com.project.rankers.ui.match

import com.project.rankers.data.model.db.LeagueItem
import com.project.rankers.data.remote.response.MatchRepo

interface MatchNavigator {
    fun handleError(throwable: Throwable)
    fun showMatchList(matchItem : List<MatchRepo.Match>)
    fun isNextActivity()
    fun failDialog()
}