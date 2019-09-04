package com.project.rankers.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

interface LoginNavigator {
    fun handleError(throwable: Throwable)

    fun openMainActivity()

    fun openRegisterActivity(email : String)

}