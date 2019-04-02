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
    var score1: ObservableField<String>
    var score2: ObservableField<String>
    var score3: ObservableField<String>
    var score5: ObservableField<String>
    var score6: ObservableField<String>
    var score9: ObservableField<String>

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
        score1 = ObservableField("입력")
        score2 = ObservableField("입력")
        score3 = ObservableField("입력")
        score5 = ObservableField("입력")
        score6 = ObservableField("입력")
        score9 = ObservableField("입력")

        number = ObservableField(group.groupNumber!!)
    }


    fun onScore1Click() {
        mListener.onScore1Click()
    }

    fun onScore2Click() {
        mListener.onScore2Click()
    }
    fun onScore3Click() {
        mListener.onScore3Click()
    }

    fun onScore5Click() {
        mListener.onScore5Click()
    }
    fun onScore6Click() {
        mListener.onScore6Click()
    }

    fun onScore9Click() {
        mListener.onScore9Click()
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface LeagueResultItemViewModelListener {
        fun onItemClick()
        fun onScore1Click()
        fun onScore2Click()
        fun onScore3Click()
        fun onScore5Click()
        fun onScore6Click()
        fun onScore9Click()
    }
}