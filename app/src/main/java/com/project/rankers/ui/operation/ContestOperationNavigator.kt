package com.project.rankers.ui.operation

interface ContestOperationNavigator {
    fun showDialog(title : String, message : String)
    fun handleError(throwable: Throwable)
}