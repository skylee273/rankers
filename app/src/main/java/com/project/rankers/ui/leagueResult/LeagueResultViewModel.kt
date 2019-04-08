package com.project.rankers.ui.leagueResult

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.databinding.ItemLeagueResultViewBinding
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LeagueResultViewModel : BaseViewModel<LeagueResultNavigator>(){
    @Inject
    internal var leagueResultAdapter: LeagueResultAdapter? = null

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

    fun updateGroup(items : MutableList<GroupResponse.Group>?){
        setIsLoading(true)
        for(item in items!!){

        }
    }
    fun onUploadClick(){
        navigator.uploadGroup()
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