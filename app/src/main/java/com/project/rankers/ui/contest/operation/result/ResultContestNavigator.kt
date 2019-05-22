package com.project.rankers.ui.contest.operation.result

interface ResultContestNavigator {
    fun handleError(throwable: Throwable)
    fun showDialog(title : String, message : String)

}