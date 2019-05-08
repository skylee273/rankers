package com.project.rankers.ui.main.contest

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContestViewModel : BaseViewModel<ContestNavigator>(){
    var mutableLiveData : MutableLiveData<List<ContestResponse.Repo>>

    init {
        mutableLiveData = MutableLiveData()
    }

    fun fetchCompetitions(){
        setIsLoading(true)
        compositeDisposable.add(Api.getContestList()
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

    fun onContestRegisteClick() {
       navigator.openContestRegister()
    }
    fun onCompetitionInfoClick() {
        navigator.openCompetitionInfo()
    }
    fun onOperatorClick(){
       navigator.openOperator()
    }
    fun onContestReultClick(){
        navigator.openContestResult()
    }


    fun getListLiveData(): MutableLiveData<List<ContestResponse.Repo>> {
        return mutableLiveData
    }
}