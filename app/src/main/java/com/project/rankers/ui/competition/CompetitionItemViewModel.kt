package com.project.rankers.ui.competition

import androidx.databinding.ObservableField
import com.project.rankers.retrofit.items.ContestItem

class CompetitionItemViewModel(contestItem: ContestItem, val mListener: CompetitionItemViewModelListner) {

    val title : ObservableField<String>
    val type : ObservableField<String>
    val date : ObservableField<String>
    val location : ObservableField<String>

    init {
        title = ObservableField(contestItem.title)
        type = ObservableField(contestItem.type)
        date = ObservableField(contestItem.start + " ~ " + contestItem.end)
        location = ObservableField(contestItem.location)
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  CompetitionItemViewModelListner{
        fun onItemClick()
    }
}