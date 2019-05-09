package com.project.rankers.ui.contest.contestResult

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContestResultViewModel: BaseViewModel<ContestResultNavigator>() {
    var mutableLiveData : MutableLiveData<List<ContestResponse.Repo>>

    init {
        mutableLiveData = MutableLiveData()
        fetchCompetitions()
    }
    fun fetchCompetitions(){
        setIsLoading(true)
        compositeDisposable.add(Api.getContestList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    navigator.updateContest(response.items)
                    mutableLiveData.value = response.items
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