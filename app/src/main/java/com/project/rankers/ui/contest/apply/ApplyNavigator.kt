package com.project.rankers.ui.contest.apply

interface ApplyNavigator {
    fun openFormActivity(departName : String, contestID : String)
    fun handleError(error : String)

}