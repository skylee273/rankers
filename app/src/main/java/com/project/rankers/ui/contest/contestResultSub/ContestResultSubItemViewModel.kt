package com.project.rankers.ui.contest.contestResultSub

import androidx.databinding.ObservableField

class ContestResultSubItemViewModel(item: String, private val mListener: ContestResultItemViewModelListener) {

    val depart : ObservableField<String>

    init {
        depart = ObservableField(item)
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  ContestResultItemViewModelListener{
        fun onItemClick()
    }
}