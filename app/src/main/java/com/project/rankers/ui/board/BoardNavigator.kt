package com.project.rankers.ui.board

interface BoardNavigator {
    fun successDialog(title : String, message : String)
    fun showDialog(title : String, message : String)
    fun handleError(throwable: Throwable)
}