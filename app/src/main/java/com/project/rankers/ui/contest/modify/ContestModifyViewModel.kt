package com.project.rankers.ui.contest.modify

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContestModifyViewModel : BaseViewModel<ContestModifyNavigator>() {
    var mutableLiveData : MutableLiveData<List<ContestResponse.Repo>>

    init {
        mutableLiveData = MutableLiveData()
        fetchCompetitions()
    }
    fun fetchCompetitions(){
        setIsLoading(true)
        compositeDisposable.add(Api.getOperationList(User().userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    //navigator.updateCompetition(response.items)
                    navigator.updateContest(response.items)
                    mutableLiveData.value = response.items
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    fun deleteContest(contestID : String){
        setIsLoading(true)
        compositeDisposable.add(Api.deleteContest(contestID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if(response.getSuccess()){
                        fetchCompetitions()
                    }
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    fun getListLiveData(): MutableLiveData<List<ContestResponse.Repo>> {
        return mutableLiveData
    }
}