package com.project.rankers.ui.main.message

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.BoardRepo
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MessageViewModel : BaseViewModel<MessageNavigator>(){
    var mutableLiveData : MutableLiveData<List<BoardRepo.Board>>

    init {
        mutableLiveData = MutableLiveData()
    }

    fun fetchCompetitions(){
        setIsLoading(true)
        compositeDisposable.add(Api.getBoardList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    navigator.updateBoard(response.items)
                    mutableLiveData.value = response.items
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })

    }

    fun updateBoards(item : BoardRepo.Board){
        var cnt = item.boardViewCnt!!.toInt()
        setIsLoading(true)
        compositeDisposable.add(Api.postBoardUpdateView(item.boardID, ++cnt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    setIsLoading(false)
                    if(response.getSuccess())
                        navigator.nextBoardActivity(item)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    fun onBoardClick(){
        navigator.openBoardActivity()
    }

    fun getListLiveData(): MutableLiveData<List<BoardRepo.Board>> {
        return mutableLiveData
    }
}