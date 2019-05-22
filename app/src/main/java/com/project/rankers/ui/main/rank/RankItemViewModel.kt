package com.project.rankers.ui.main.rank

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.RankRepo

class RankItemViewModel(items: RankRepo.Rank, private val mListener: RankItemViewModelListener) {

    val rank : ObservableField<String>
    val name : ObservableField<String>
    val rate : ObservableField<String>
    val total : ObservableField<String>
    val win : ObservableField<String>
    val lose : ObservableField<String>
    var count = 1
    init {

        rank = ObservableField((count++).toString())
        name = ObservableField(items.rankName + "(" + items.rankEmail + ")")
        rate = ObservableField(items.rankRate!!)
        total = ObservableField(items.rankTotal!!)
        win = ObservableField(items.rankWin!!)
        lose = ObservableField(items.rankLose!!)

    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  RankItemViewModelListener{
        fun onItemClick()
    }
}