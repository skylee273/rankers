package com.project.rankers.ui.contest.leagueResult

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LeagueResultViewModel : BaseViewModel<LeagueResultNavigator>(){

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
        for(item in items!!){
            setIsLoading(true)
            compositeDisposable.add(Api.postUpdateGroup(contestID, contestDepartName, item.groupNumber, item.groupScore1, item.groupScore2, item.groupScore3, item.groupScore4,
                    item.groupScore5, item.groupScore6, item.groupGain1, item.groupGain2, item.groupGain3, item.groupGain4, item.groupTotal1, item.groupTotal2, item.groupTotal3,
                    item.groupTotal4, item.groupRank1, item.groupRank2, item.groupRank3, item.groupRank4)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        setIsLoading(false)
                    }) {
                        setIsLoading(false)
                        navigator.handleError(it)
                    })
        }
        navigator.showDialog("예선전 결과표", "예선결과 수정이 완료되었습니다")
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