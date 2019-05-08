package com.project.rankers.ui.contest.form

class ApplicationFormItemEmptyViewModel (private val mListener: FormEmptyItemViewModelListener) {

    fun onRetryClick() {
        mListener.onRetryClick()
    }

    interface FormEmptyItemViewModelListener {
        fun onRetryClick()
    }
}