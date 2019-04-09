package com.project.rankers.ui.league

import com.project.rankers.data.model.db.LeagueItem

interface LeagueNavigator {
    fun handleError(throwable: Throwable)
    fun setGroupCount(count : Int, num : Int, peopleItem : MutableList<String>)
    fun showGroupNumber(num : Int)
    fun uploadLeague()
    fun showRecyclerView(items : ArrayList<LeagueItem>)
    fun isNextActivity()
    fun failDialog()
}