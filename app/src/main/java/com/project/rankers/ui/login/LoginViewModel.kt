package com.project.rankers.ui.login


import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.data.model.db.USER
import com.project.rankers.data.remote.api.Api
import io.reactivex.schedulers.Schedulers


class LoginViewModel: BaseViewModel<LoginNavigator>() {
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