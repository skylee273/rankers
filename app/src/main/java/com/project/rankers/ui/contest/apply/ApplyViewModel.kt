package com.project.rankers.ui.contest.apply

import androidx.databinding.ObservableField
import com.project.rankers.ui.base.BaseViewModel

class ApplyViewModel : BaseViewModel<ApplyNavigator>() {
    var isDepart : Boolean? = false
    var title: ObservableField<String>
    var date: ObservableField<String>
    var location: ObservableField<String>
    var host: ObservableField<String>
    var departName : String? = null
    var contestID : String? = null

    init {
        title = ObservableField("")
        date = ObservableField("")
        location = ObservableField("")
        host = ObservableField("")
    }

    fun onApplyClick(){
        if(isDepart!!){
            navigator.openFormActivity(departName!!, contestID!!)
        }else{
            navigator.handleError("등록오류")
        }
    }
    fun setApply(title : String, date : String, location : String, host : String, contestID : String){
        this.title.set(title)
        this.date.set(date)
        this.location.set(location)
        this.host.set(host)
        this.contestID = contestID
    }
}