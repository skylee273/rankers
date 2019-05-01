package com.project.rankers.ui.contestRegister

interface ContestRegisterNavigator {
    fun showDialog(title : String, message : String)
    fun showLocationDialog(title : String)
    fun showTypeDialog(items : List<String>)
    fun showDateDialog(type : Int)
    fun showFileDialog()
    fun showDepartDialog(hint : String)
    fun showFaildDialog()
    fun handleError(throwable: Throwable)
}