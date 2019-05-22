package com.project.rankers.ui.contest.operation.manager

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.UserRepo
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ManagerViewModel : BaseViewModel<ManagerNavigator>(){
    var mutableLiveData : MutableLiveData<List<UserRepo.User>>

    init {
        mutableLiveData = MutableLiveData()
    }

    fun fetchCompetitions(){
        setIsLoading(true)
        compositeDisposable.add(Api.getUserAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    navigator.showUserList(response.items)
                    mutableLiveData.value = response.items
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })

    }
    fun getListLiveData(): MutableLiveData<List<UserRepo.User>> {
        return mutableLiveData
    }
}