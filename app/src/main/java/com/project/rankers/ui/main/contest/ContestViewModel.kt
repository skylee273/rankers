package com.project.rankers.ui.main.contest

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.ui.competition.CompetitionInfoActivity
import com.project.rankers.ui.competition.CompetitionInfoNavigator
import com.project.rankers.ui.operator.OperatorActivity
import com.project.rankers.ui.register.ContestRegisterActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ContestViewModel : BaseViewModel<ContestNavigator>(){
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


    fun getListLiveData(): MutableLiveData<List<ContestResponse.Repo>> {
        return mutableLiveData
    }
}