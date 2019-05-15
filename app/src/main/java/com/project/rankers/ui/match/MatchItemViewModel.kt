package com.project.rankers.ui.match

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.MatchRepo

class MatchItemViewModel(items: MatchRepo.Match, private val mListener: MatchItemViewModelListener) {

    val my : ObservableField<String>
    val other : ObservableField<String>
    val date : ObservableField<String>
    val score : ObservableField<String>
    val result : ObservableField<String>
    val isWin = ObservableBoolean()

    init {
        if(items.matchPartner != "-"){
            my = ObservableField(items.matchMy!! + "," + items.matchPartner)
            other = ObservableField(items.matchOther!! + "," + items.matchOtherPartner)
        }else{
            my = ObservableField(items.matchMy!!)
            other = ObservableField(items.matchOther!!)
        }
        date = ObservableField(items.matchDate!!)
        score = ObservableField(items.matchWin!! +  " : " + items.matchLose)
        result = if(items.matchResult == "승"){
            setIsState(true)
            ObservableField("승")
        } else{
            setIsState(false)
            ObservableField("패")
        }

    }

    private fun setIsState(isWin: Boolean) {
        this.isWin.set(isWin)
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  MatchItemViewModelListener{
        fun onItemClick()
    }
}