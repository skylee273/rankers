package com.project.rankers.ui.match

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.MatchRepo

class MatchItemViewModel(items: MatchRepo.Match, private val mListener: MatchItemViewModelListener) {

    val my : ObservableField<String>
    val partner : ObservableField<String>
    val other : ObservableField<String>
    val otherPartner : ObservableField<String>
    val date : ObservableField<String>
    val score : ObservableField<String>
    val result : ObservableField<String>

    init {
        my = ObservableField(items.matchMy!!)
        partner = ObservableField(items.matchPartner!!)
        other = ObservableField(items.matchOther!!)
        otherPartner = ObservableField(items.matchOtherPartner!!)
        date = ObservableField(items.matchDate!!)
        score = ObservableField(items.matchWin!! +  " : " + items.matchLose)
        result = if(items.matchResult == "승")
            ObservableField("승")
        else
            ObservableField("패")
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  MatchItemViewModelListener{
        fun onItemClick()
    }
}