package com.project.rankers.ui.record.single

import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.utils.CommonUtils
import io.reactivex.schedulers.Schedulers

class SingleViewModel : BaseViewModel<SingleNavigator>(){

    var date = ObservableField<String>()
    var other = ObservableField<String>()
    var myScore = ObservableField<String>()
    var otherScore = ObservableField<String>()

    private fun getDate(): String? {
        return date.get()
    }

    private fun getOther(): String? {
        return other.get()
    }


    private fun getMyScore(): String? {
        return myScore.get()
    }

    private fun getOtherScore(): String? {
        return otherScore.get()
    }

    fun onUploadClick(){
        if(checkDraw()){
            setIsLoading(true)
            if(isEmptyText()){
                compositeDisposable.add(Api.postMatchCreator(User().userID, "1", User().userName, "-", getOther(), "-", getDate(), checkResult(), getMyScore(), getOtherScore())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({
                            setIsLoading(false)
                            if (it.getSuccess())
                                navigator.showSuccessDialog()
                        }) {
                            navigator.handleError(it)
                            setIsLoading(false)
                        })
            }else{
                setIsLoading(false)
                navigator.showFaildDialog()
            }
        }else{
            navigator.showDrawDialog()
        }
    }
    private fun checkDraw() : Boolean {
        if(getMyScore()!!.toInt() == getOtherScore()!!.toInt()){
            return false
        }
        return true
    }
    private fun checkResult() : String{
        var strResult = ""
        if(getMyScore()!!.toInt() > getOtherScore()!!.toInt()){
            strResult = "승"
        }else if (getMyScore()!!.toInt() < getOtherScore()!!.toInt()){
            strResult = "패"
        }
        return strResult
    }
    fun onDateClick() {
        navigator.showDateDialog()
    }
    private fun isEmptyText(): Boolean {
        return (!CommonUtils.isEmpty(User().userID) && !CommonUtils.isEmpty(getDate()) && !CommonUtils.isEmpty(getOther())
                && !CommonUtils.isEmpty(getMyScore()) && !CommonUtils.isEmpty(getOtherScore()))
    }

}