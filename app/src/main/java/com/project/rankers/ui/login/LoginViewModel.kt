package com.project.rankers.ui.login


import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.data.remote.api.Api
import io.reactivex.schedulers.Schedulers


class LoginViewModel: BaseViewModel<LoginNavigator>() {


    fun onGoogleLoginClick() {
        setIsLoading(true)
        navigator.googleLogin()
    }

    fun onNaverLogin(token : String){
        setIsLoading(true)
        isUser("hnlee@btcore.net","이하늘")
    }

    fun isUser(email: String?, nickName: String?) {
        compositeDisposable.add(Api.getID(email)
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if (it.getSuccess()) {
                        setIsLoading(false)
                        navigator.openMainActivity()
                    } else {
                        navigator.openRegisterActivity(email!!)
                    }
                }) {
                    // 에러블록
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }


}