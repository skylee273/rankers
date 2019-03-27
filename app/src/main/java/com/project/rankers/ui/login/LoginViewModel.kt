package com.project.rankers.ui.login


import com.google.android.gms.tasks.Task
import com.project.rankers.base.BaseViewModel
import com.project.rankers.model.USER
import com.project.rankers.retrofit.api.Api
import io.reactivex.schedulers.Schedulers

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class LoginViewModel : BaseViewModel<LoginNavigator>() {
    var user: USER? = null


    fun onGoogleLoginClick() {
        setIsLoading(true)
        navigator.googleLogin()
    }

    fun onNaverLogin(token : String){
        setIsLoading(true)
        isUser("hnlee@btcore.net","이하늘")
    }

    fun isUser(email: String?, nickName: String?) {
        user = USER()
        compositeDisposable.add(Api.getID(email)
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if (it.getSuccess()) {
                        setIsLoading(false)
                        user!!.seteMail(email)
                        navigator.openMainActivity()
                    } else {
                        createUser(email, nickName)
                    }
                }) {
                    // 에러블록
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }


    private fun createUser(email: String?, nickName: String?) {
        compositeDisposable.add(Api.postUserCreator(email, nickName)
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if (it.getSuccess()) {
                        setIsLoading(false)
                        user!!.seteMail(email)
                        navigator.openMainActivity()
                    }
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

}