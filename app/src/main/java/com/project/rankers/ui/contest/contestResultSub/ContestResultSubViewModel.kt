package com.project.rankers.ui.contest.contestResultSub

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContestResultSubViewModel : BaseViewModel<ContestResultSubNavigator>(){

    var mutableLiveData : MutableLiveData<List<String>>
    var contestID: String? = null
    var contestDepartName: String? = null

    var arrayDepart : List<String>? = null

    init {
        mutableLiveData = MutableLiveData()
        arrayDepart = ArrayList()
    }
    fun fetchCompetitions(){
        setIsLoading(true)
        compositeDisposable.add(Api.getContestAllByIds(contestID!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    for(item in response.items){
                        arrayDepart = item.depart!!.split(",")
                    }
                    navigator.updateContest(arrayDepart!!)
                    mutableLiveData.value = arrayDepart
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }


    fun getListLiveData(): MutableLiveData<List<String>> {
        return mutableLiveData
    }

    fun setContestInfo(contestID: String, contestDepartName: String) {
        this.contestID = contestID
        this.contestDepartName = contestDepartName
        fetchCompetitions()
    }

}