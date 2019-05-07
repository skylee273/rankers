package com.project.rankers.ui.record.multi

interface MultiNavigator {
    fun showDateDialog()
    fun showFaildDialog()
    fun showSuccessDialog()

    fun handleError(throwable: Throwable)

}