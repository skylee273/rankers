package com.project.rankers.ui.contest.operator

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.ContestResponse

class OperatorItemViewModel(items: ContestResponse.Repo, private val mListener: OperatorItemViewModelListener) {

    val title : ObservableField<String>
    val type : ObservableField<String>
    val date : ObservableField<String>
    val location : ObservableField<String>

    init {
        title = ObservableField(items.title!!)
        type = ObservableField(items.type!!)
        date = ObservableField(items.start!! + " ~ " + items.end)
        location = ObservableField(items.location!!)
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface  OperatorItemViewModelListener{
        fun onItemClick()
    }
}