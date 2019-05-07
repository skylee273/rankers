package com.project.rankers.ui.match

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.ContestResponse
import com.project.rankers.data.remote.response.MatchRepo
import com.project.rankers.data.remote.response.MultiRepo
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MatchViewModel : BaseViewModel<MatchNavigator>(){


    var mutableLiveData : MutableLiveData<List<MatchRepo.Match>>


    init {
        mutableLiveData = MutableLiveData()
        fetchMatches()
    }


    private fun fetchMatches() {
        compositeDisposable.add(Api.getMatchList(User().userID!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    navigator.showMatchList(response.items)
                }, { error: Throwable ->
                    navigator.handleError(error)
                })
        )
    }

    fun getListLiveData(): MutableLiveData<List<MatchRepo.Match>> {
        return mutableLiveData
    }


}