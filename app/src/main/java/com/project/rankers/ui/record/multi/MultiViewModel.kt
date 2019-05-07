package com.project.rankers.ui.record.multi

import android.util.Log
import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.utils.CommonUtils
import io.reactivex.schedulers.Schedulers

class MultiViewModel : BaseViewModel<MultiNavigator>(){

    var date = ObservableField<String>()
    var partner = ObservableField<String>()
    var other = ObservableField<String>()
    var otherPatner = ObservableField<String>()
    var myScore = ObservableField<String>()
    var otherScore = ObservableField<String>()

    private fun getDate(): String? {
        return date.get()
    }

    private fun getPartner(): String? {
        return partner.get()
    }

    private fun getOther(): String? {
        return other.get()
    }

    private fun getOtherPartner(): String? {
        return otherPatner.get()
    }

    private fun getMyScore(): String? {
        return myScore.get()
    }

    private fun getOtherScore(): String? {
        return otherScore.get()
    }

    fun onUploadClick(){
        setIsLoading(true)
        if(isEmptyText()){
            compositeDisposable.add(Api.postMatchCreator(User().userID, "1", User().userName, getPartner(), getOther(), getOtherPartner(), getDate(), checkResult(),getMyScore(), getOtherScore())
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
    }
    private fun checkResult() : String{
        return if(getMyScore()!!.toInt() >= getOtherScore()!!.toInt())
            "승"
        else
            "패"
    }
    fun onDateClick() {
        navigator.showDateDialog()
    }
    private fun isEmptyText(): Boolean {
        return (!CommonUtils.isEmpty(User().userID) && !CommonUtils.isEmpty(getDate()) && !CommonUtils.isEmpty(getOther())
                && !CommonUtils.isEmpty(getPartner()) && !CommonUtils.isEmpty(getOtherPartner()) && !CommonUtils.isEmpty(getMyScore()) && !CommonUtils.isEmpty(getOtherScore()))
    }

}