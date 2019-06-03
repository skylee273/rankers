package com.project.rankers.ui.contest.contestRegister

import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.DepartItem

class ContestRegisterItemViewModel(departItem: DepartItem, val mListener: ContestRegisterItemViewModelListner) {
    val depart : ObservableField<String>

    init {
        depart = ObservableField(departItem.depart)
    }

    fun onRemoveClick(){
        mListener.onRemoveClick()
    }
    interface  ContestRegisterItemViewModelListner{
        fun onRemoveClick()
    }


}