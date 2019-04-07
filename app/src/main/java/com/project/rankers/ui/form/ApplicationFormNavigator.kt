package com.project.rankers.ui.form

interface ApplicationFormNavigator {
    fun showDialog(title : String, message : String)
    fun handleError(throwable: Throwable)
}