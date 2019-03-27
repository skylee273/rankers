package com.project.rankers.ui.competition

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.rankers.adapter.ContestAdapter
import com.project.rankers.base.BaseViewModel
import com.project.rankers.retrofit.api.Api
import com.project.rankers.retrofit.items.ContestItem
import com.project.rankers.retrofit.models.ContestRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CompetitionInfoViewModel : BaseViewModel<CompetitionInfoNavigator>(){

    private lateinit var mutableLiveData : MutableLiveData<List<ContestItem>>

    fun CompetitionInfoViewModel(){
        mutableLiveData =  MutableLiveData()
        fetchCompetitions()
    }

    fun fetchCompetitions(){
        setIsLoading(true)
        compositeDisposable.add(Api.getContestList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: ContestRepo ->
                    navigator.updateCompetition(response.items)
                    mutableLiveData.value = response.items
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }
    fun getCompetitionListLiveData(): LiveData<List<ContestItem>> {
        return mutableLiveData
    }

}