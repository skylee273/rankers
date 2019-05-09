package com.project.rankers.ui.contest.contestResultLeague

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContestResultLeagueViewModel : BaseViewModel<ContestResultLeagueNavigator>(){

    var mutableLiveData : MutableLiveData<List<GroupResponse.Group>>
    var contestID : String? = null
    var contestDepartName : String? = null
    init {
        mutableLiveData = MutableLiveData()
    }

    fun fetchGroups(){
        setIsLoading(true)
        compositeDisposable.add(Api.getGroupList(contestID, contestDepartName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: GroupResponse ->
                    navigator.updateGroup(response.items)
                    mutableLiveData.postValue(response.items)
                    mutableLiveData.value = response.items
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    fun setContestInfo(contestID : String, contestDepartName : String){
        this.contestID = contestID
        this.contestDepartName = contestDepartName
        fetchGroups()
    }

    fun getListLiveData(): MutableLiveData<List<GroupResponse.Group>> {
        return mutableLiveData
    }
}