package com.project.rankers.ui.register

import androidx.databinding.ObservableField
import com.kakao.usermgmt.response.model.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.utils.CommonUtils
import io.reactivex.schedulers.Schedulers

class RegisterViewModel : BaseViewModel<RegisterNavigator>() {

    private var userEmail : String? = null
    val userName = ObservableField<String>()
    val userPhone = ObservableField<String>()
    val userBirthday = ObservableField<String>()

    private fun getUserName(): String? {
        return userName.get()
    }

    private fun getUserPhone(): String? {
        return userPhone.get()
    }

    private fun getUserBirthday(): String? {
        return userBirthday.get()
    }
    fun setUserEmail(email: String?){
        userEmail = email
    }
    private fun getUserEmail() : String?{
        return userEmail
    }

    private fun checkEmpty() : Boolean?{
        return !(CommonUtils.isEmpty(getUserEmail()) || CommonUtils.isEmpty(getUserName()) || CommonUtils.isEmpty(getUserPhone()) || CommonUtils.isEmpty(getUserBirthday()))
    }
    fun onFormClick() {
        if (checkEmpty()!!) {
            createUser(getUserEmail(), getUserName(), getUserPhone(), getUserBirthday())
        }else{
            navigator.showRetryDialog()
        }
    }

    private fun createUser(email: String?, nickName: String?, phone : String?, birthday : String?) {
        setIsLoading(true)
        compositeDisposable.add(Api.postUserCreator(email, nickName, phone, birthday)
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if (it.getSuccess()) {
                        updateUserInfo(email)
                    }
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })

    }

    private fun updateUserInfo(email: String?) {
        val user =com.project.rankers.data.model.db.User()
        compositeDisposable.add(Api.getUserAllByIds(email)
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    setIsLoading(true)
                    val item = it.items[0]
                    if(item.userID!!.isNotEmpty()){
                        user.userID = item.userID
                        user.userName = item.userName
                        user.userEmail = item.userEmail
                        user.userPhone = item.userPhone
                        user.userBirthday = item.userBirthday
                        navigator.openMainActivity()
                    }
                    else
                        navigator.showRetryDialog()

                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }



}