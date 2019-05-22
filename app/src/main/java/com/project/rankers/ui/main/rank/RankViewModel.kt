package com.project.rankers.ui.main.rank

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.RankRepo
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RankViewModel : BaseViewModel<RankNavigator>(){
    var mutableLiveData : MutableLiveData<List<RankRepo.Rank>>

    init {
        mutableLiveData = MutableLiveData()
    }

    fun fetchCompetitions(){
        setIsLoading(true)
        compositeDisposable.add(Api.getRankList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    //navigator.updateCompetition(response.items)
                    navigator.showRankList(response.items)
                    mutableLiveData.value = response.items
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })

    }
    fun getListLiveData(): MutableLiveData<List<RankRepo.Rank>> {
        return mutableLiveData
    }
}