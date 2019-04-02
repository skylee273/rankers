package com.project.rankers.ui.leagueResult

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.ui.competition.CompetitionInfoNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LeagueResultViewModel : BaseViewModel<LeagueResultNavigator>(){

    var mutableLiveData : MutableLiveData<List<GroupResponse.Group>>
    var contestID : String? = null
    var contestDepartName : String? = null
    init {
        mutableLiveData = MutableLiveData()
        fetchGroups()
    }

    fun fetchGroups(){
        setIsLoading(true)

        compositeDisposable.add(Api.getGroupList(contestID, contestDepartName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: GroupResponse ->
                    navigator.updateGroup(response.items)
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
    }
    fun getListLiveData(): MutableLiveData<List<GroupResponse.Group>> {
        return mutableLiveData
    }
}