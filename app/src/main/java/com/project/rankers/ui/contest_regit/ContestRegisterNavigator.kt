package com.project.rankers.ui.contest_regit

interface ContestRegisterNavigator {
    fun showDialog(title : String, message : String)
    fun showLocationDialog(title : String)
    fun showTypeDialog(items : List<String>)
    fun showDateDialog(type : Int)
    fun showFileDialog()
    fun showDepartDialog(hint : String)
    fun handleError(throwable: Throwable)
}