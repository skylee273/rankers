package com.project.rankers.ui.contest.contestResultLeague

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.GroupResponse

class ContestResultLeagueItemViewModel(group: GroupResponse.Group, private val mListener: LeagueResultItemViewModelListener) {


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
    var score4: ObservableField<String>
    var score5: ObservableField<String>
    var score6: ObservableField<String>
    var score7: ObservableField<String>
    var score8: ObservableField<String>
    var score9: ObservableField<String>
    var score10: ObservableField<String>
    var score11: ObservableField<String>
    var score12: ObservableField<String>


    var gain1 : ObservableField<String>
    var gain2 : ObservableField<String>
    var gain3 : ObservableField<String>
    var gain4 : ObservableField<String>

    var totalScore1: ObservableField<String>
    var totalScore2: ObservableField<String>
    var totalScore3: ObservableField<String>
    var totalScore4: ObservableField<String>

    var rank1: ObservableField<String>
    var rank2: ObservableField<String>
    var rank3: ObservableField<String>
    var rank4: ObservableField<String>

    val number: ObservableField<String>

    init {
        player1 = ObservableField(group.groupPlayer1!!)
        player2 = ObservableField(group.groupPlayer2!!)
        player3 = ObservableField(group.groupPlayer3!!)
        player4 = ObservableField(group.groupPlayer4!!)
        player5 = ObservableField(group.groupPlayer1)
        player6 = ObservableField(group.groupPlayer2)
        player7 = ObservableField(group.groupPlayer3)
        player8 = ObservableField(group.groupPlayer4)

        score1 =  ObservableField(set(group.groupScore1!!))

        score2 = ObservableField(set(group.groupScore2!!))

        score3 = ObservableField(set(group.groupScore3!!))

        score4 = ObservableField(set(group.groupScore4!!))

        score5 = ObservableField(set(group.groupScore5!!))

        score6 = ObservableField(set(group.groupScore6!!))

        score7 = ObservableField(group.groupScore1!!.reversed())

        score8 = ObservableField(group.groupScore2!!.reversed())

        score9 = ObservableField(group.groupScore3!!.reversed())

        score10 = ObservableField(group.groupScore4!!.reversed())

        score11 = ObservableField(group.groupScore5!!.reversed())

        score12 = ObservableField(group.groupScore6!!.reversed())

        gain1 = ObservableField(set(group.groupGain1!!))
        gain2 = ObservableField(set(group.groupGain2!!))
        gain3 = ObservableField(set(group.groupGain3!!))
        gain4 = ObservableField(set(group.groupGain4!!))


        totalScore1 = ObservableField(set(group.groupTotal1!!))
        totalScore2 = ObservableField(set(group.groupTotal2!!))
        totalScore3 = ObservableField(set(group.groupTotal3!!))
        totalScore4 = ObservableField(set(group.groupTotal4!!))

        rank1 = ObservableField(set(group.groupRank1!!))
        rank2 = ObservableField(set(group.groupRank2!!))
        rank3 = ObservableField(set(group.groupRank3!!))
        rank4 = ObservableField(set(group.groupRank4!!))

        number = ObservableField(group.groupNumber!! + "조 매치")
    }

    fun set(str: String): String {
        return if (str == "") {
            "입력"
        } else {
            str
        }
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

    fun onScore4Click() {
        mListener.onScore4Click()
    }

    fun onScore5Click() {
        mListener.onScore5Click()
    }

    fun onScore6Click() {
        mListener.onScore6Click()
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface LeagueResultItemViewModelListener {
        fun onItemClick()
        fun onScore1Click()
        fun onScore2Click()
        fun onScore3Click()
        fun onScore4Click()
        fun onScore5Click()
        fun onScore6Click()

    }
}