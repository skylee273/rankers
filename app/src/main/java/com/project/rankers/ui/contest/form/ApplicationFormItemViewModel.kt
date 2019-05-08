package com.project.rankers.ui.contest.form

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.response.MatchRepo
import com.project.rankers.data.remote.response.UserRepo

class ApplicationFormItemViewModel(items: UserRepo.User, private val mListener: FormItemViewModelListener) {

    val name: ObservableField<String>
    val email: ObservableField<String>


    init {
        name = ObservableField(items.userName!!)
        email = ObservableField(items.userEmail!!)
    }

    fun onUpdatePartner() {
        mListener.onUpdatePartner()
    }

    interface FormItemViewModelListener {
        fun onUpdatePartner()
    }
}