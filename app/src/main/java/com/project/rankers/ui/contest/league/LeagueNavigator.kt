package com.project.rankers.ui.contest.league

import com.project.rankers.data.model.db.LeagueItem

interface LeagueNavigator {
    fun handleError(throwable: Throwable)
    fun setGroupCount(count : Int, num : Int, peopleItem : ArrayList<String>)
    fun showGroupNumber(num : Int)
    fun uploadLeague()
    fun showRecyclerView(items : ArrayList<LeagueItem>)
    fun isNextActivity()
    fun failDialog()
}