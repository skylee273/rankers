package com.project.rankers.ui.contest.contestRegister

interface ContestRegisterNavigator {
    fun showDialog(title : String, message : String)
    fun showTypeDialog(items : List<String>)
    fun showDateDialog(type : Int)
    fun showFileDialog()
    fun showDepartDialog(hint : String)
    fun showFaildDialog()
    fun handleError(throwable: Throwable)
}