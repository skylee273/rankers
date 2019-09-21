package com.project.rankers.ui.login


import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.schedulers.Schedulers


class LoginViewModel: BaseViewModel<LoginNavigator>() {

    fun isUser(email: String?, nickName: String?) {
        compositeDisposable.add(Api.getID(email)
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if (it.getSuccess()) {
                        updateUserInfo(email)
                    } else {
                        navigator.openRegisterActivity(email!!)
                    }
                }) {
                    // 에러블록
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    private fun updateUserInfo(email: String?) {
        val user = User()
        compositeDisposable.add(Api.getUserAllByIds(email)
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    setIsLoading(true)

                    val item = it.items[0]
                    if(item.userID!!.isNotEmpty()){
                        user.userAdmin = item.userAdmin
                        user.userID = item.userID
                        user.userName = item.userName
                        user.userEmail = item.userEmail
                        user.userPhone = item.userPhone
                        user.userBirthday = item.userBirthday
                        navigator.openMainActivity()
                    }
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }


}