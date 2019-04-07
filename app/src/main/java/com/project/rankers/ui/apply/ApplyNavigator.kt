package com.project.rankers.ui.apply

interface ApplyNavigator {
    fun openFormActivity(departName : String, contestID : String)
    fun handleError(error : String)

}