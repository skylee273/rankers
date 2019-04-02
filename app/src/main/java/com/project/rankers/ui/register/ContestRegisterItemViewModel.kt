package com.project.rankers.ui.register

import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.DepartItem

class ContestRegisterItemViewModel(departItem: DepartItem, val mListener: ContestRegisterItemViewModelListner) {
    val name : ObservableField<String>
    init {
        name = ObservableField(departItem.depart)
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  ContestRegisterItemViewModelListner{
        fun onItemClick()
    }


}