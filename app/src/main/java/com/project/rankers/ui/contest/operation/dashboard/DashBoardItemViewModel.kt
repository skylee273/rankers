package com.project.rankers.ui.contest.operation.dashboard

import androidx.databinding.ObservableField

class DashBoardItemViewModel(items: String, private val mListener: DashboardItemViewListener) {

    val depart : ObservableField<String>


    init {
        depart = ObservableField(items)
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  DashboardItemViewListener{
        fun onItemClick()
    }
}