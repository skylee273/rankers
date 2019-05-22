package com.project.rankers.ui.contest.modify

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.ContestResponse

class ContestModifyItemViewModel (items: ContestResponse.Repo, private val mListener: ModifyItemViewModelListener) {

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

    interface  ModifyItemViewModelListener{
        fun onItemClick()
    }
}