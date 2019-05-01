package com.project.rankers.ui.contestRegister

import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.DepartItem

class ContestRegisterItemViewModel(departItem: DepartItem, val mListener: ContestRegisterItemViewModelListner) {
    val depart : ObservableField<String>
    init {
        depart = ObservableField(departItem.depart)
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  ContestRegisterItemViewModelListner{
        fun onItemClick()
    }


}