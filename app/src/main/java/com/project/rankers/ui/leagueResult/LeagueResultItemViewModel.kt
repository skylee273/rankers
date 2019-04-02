package com.project.rankers.ui.leagueResult

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.GroupResponse

class LeagueResultItemViewModel(group: GroupResponse.Group, private val mListener: LeagueResultItemViewModelListener) {


    val player1: ObservableField<String>
    val player2: ObservableField<String>
    val player3: ObservableField<String>
    val player4: ObservableField<String>
    val player5: ObservableField<String>
    val player6: ObservableField<String>
    val player7: ObservableField<String>
    val player8: ObservableField<String>
    val number: ObservableField<String>

    init {
        val groupMember = group.groupMember!!.split(",")
        player1 = ObservableField(groupMember[0])
        player2 = ObservableField(groupMember[1])
        player3 = ObservableField(groupMember[2])
        player4 = ObservableField(groupMember[3])
        player5 = ObservableField(groupMember[0])
        player6 = ObservableField(groupMember[1])
        player7 = ObservableField(groupMember[2])
        player8 = ObservableField(groupMember[3])
        number = ObservableField(group.groupNumber!!)
    }


    fun onItemClick() {
        mListener.onItemClick()
    }

    interface LeagueResultItemViewModelListener {
        fun onItemClick()
    }
}