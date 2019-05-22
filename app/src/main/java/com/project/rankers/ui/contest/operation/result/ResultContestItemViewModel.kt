package com.project.rankers.ui.contest.operation.result

import androidx.databinding.ObservableField

class ResultContestItemViewModel(items: String, private val mListener: ReusltContestItemViewListener) {

    val depart : ObservableField<String>


    init {
        depart = ObservableField(items)
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  ReusltContestItemViewListener{
        fun onItemClick()
    }
}