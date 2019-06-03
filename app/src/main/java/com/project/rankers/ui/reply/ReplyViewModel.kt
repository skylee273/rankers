package com.project.rankers.ui.reply

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.ReplyRepo
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReplyViewModel : BaseViewModel<ReplyNavigator>() {
    var title: ObservableField<String>
    var name: ObservableField<String>
    var date: ObservableField<String>
    var view_cnt: ObservableField<String>
    var reply_cnt: ObservableField<String>
    var text : ObservableField<String>
    var reply = ObservableField<String>()
    var boardID : String? = null
    var replyCnt : Int = 0
    var mutableLiveData : MutableLiveData<List<ReplyRepo.Reply>>

    private fun getReply(): String? {
        return reply.get()
    }

    init {
        mutableLiveData = MutableLiveData()
        title = ObservableField("")
        name = ObservableField("")
        date = ObservableField("")
        view_cnt = ObservableField("")
        reply_cnt = ObservableField("")
        text = ObservableField("")
    }

    private fun fetchReplys(){
        setIsLoading(true)
        compositeDisposable.add(Api.getReplyList(boardID!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    navigator.showReplyList(response.items)
                    replyCnt = response.items.size
                    this.reply_cnt.set("댓글 $replyCnt")
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })

    }

    fun onReplyClick(){
        setIsLoading(true)
        if(checkLengthText(getReply()!!)){
            compositeDisposable.add(Api.postReply(boardID,name.get()!!,getReply()!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        if(response.getSuccess()){
                            updateReplyCnt()
                        }
                        setIsLoading(false)
                    }) {
                        setIsLoading(false)
                        navigator.handleError(it)
                    })
        }else{
            navigator.showLengthDialog()
        }
    }
    private fun updateReplyCnt(){
        var cnt = replyCnt
        setIsLoading(true)
        compositeDisposable.add(Api.postBoardUpdateReply(boardID!!, ++cnt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    setIsLoading(false)
                    if(response.getSuccess()){
                        fetchReplys()
                    }
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    private fun checkLengthText(reply : String) : Boolean{
        return reply.length <= 80
    }
    fun setBoard(title : String, name : String, date : String, view_cnt : String, reply_cnt : String, text : String, boardID : String){
        this.title.set(title)
        this.name.set(name)
        this.date.set(date)
        this.view_cnt.set("조회 $view_cnt")
        this.replyCnt = replyCnt
        this.reply_cnt.set("댓글 $reply_cnt")
        this.text.set(text)
        this.boardID = boardID
        fetchReplys()
    }
    fun getListLiveData(): MutableLiveData<List<ReplyRepo.Reply>> {
        return mutableLiveData
    }

}