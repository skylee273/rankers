package com.project.rankers.ui.record.single

interface SingleNavigator {
    fun showDateDialog()
    fun showFaildDialog()
    fun showSuccessDialog()
    fun showDrawDialog()
    fun handleError(throwable: Throwable)
}