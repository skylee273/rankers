package com.project.rankers.ui.contest.operation.manager

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.UserRepo

class ManagerItemViewModel(items: UserRepo.User, private val mListener: ManagerItemViewModelListener) {

    val name : ObservableField<String>
    val email : ObservableField<String>
    init {
        name = ObservableField(items.userName!!)
        email = ObservableField(items.userEmail!!)
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  ManagerItemViewModelListener{
        fun onItemClick()
    }
}