package com.project.rankers.ui.contest.operation

interface ContestOperationNavigator {
    fun showDialog(title : String, message : String)
    fun handleError(throwable: Throwable)
}