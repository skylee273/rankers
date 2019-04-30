package com.project.rankers.ui.register

interface RegisterNavigator {
    fun openMainActivity()
    fun handleError(throwable: Throwable)
    fun showRetryDialog()
}