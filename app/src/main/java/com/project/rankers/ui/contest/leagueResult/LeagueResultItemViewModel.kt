package com.project.rankers.ui.contest.leagueResult

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

        score1 = if(group.groupScore1 == "0")
            ObservableField("입력")
        else{
            ObservableField(set(group.groupScore1!!))
        }

        score2 = if(group.groupScore2 == "0")
            ObservableField("입력")
        else
            ObservableField(set(group.groupScore2!!))

        score3 = if(group.groupScore3 == "0")
            ObservableField("입력")
        else
            ObservableField(set(group.groupScore3!!))

        score4 = if(group.groupScore4 == "0")
            ObservableField("입력")
        else
            ObservableField(set(group.groupScore4!!))

        score5 = if(group.groupScore5 == "0")
            ObservableField("입력")
        else
            ObservableField(set(group.groupScore5!!))

        score6 = if(group.groupScore6 == "0")
            ObservableField("입력")
        else
            ObservableField(set(group.groupScore6!!))

        score7 = if(group.groupScore1 == "0")
            ObservableField("")
        else
            ObservableField(group.groupScore1!!.reversed())

        score8 = if(group.groupScore2 == "0")
            ObservableField("")
        else
            ObservableField(group.groupScore2!!.reversed())

        score9 = if(group.groupScore3 == "0")
            ObservableField("")
        else
            ObservableField(group.groupScore3!!.reversed())

        score10 = if(group.groupScore4 == "0")
            ObservableField("")
        else
            ObservableField(group.groupScore4!!.reversed())

        score11 = if(group.groupScore5 == "0")
            ObservableField("")
        else
            ObservableField(group.groupScore5!!.reversed())

        score12 = if(group.groupScore6 == "0")
            ObservableField("")
        else
            ObservableField(group.groupScore6!!.reversed())

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
        if(score1.get() == "입력"){
            mListener.onScore1Click()
        }
    }

    fun onScore2Click() {
        if(score2.get() == "입력"){
            mListener.onScore2Click()
        }
    }

    fun onScore3Click() {
        if(score3.get() == "입력"){
            mListener.onScore3Click()
        }
    }

    fun onScore4Click() {
        if(score4.get() == "입력"){
            mListener.onScore4Click()
        }
    }

    fun onScore5Click() {
        if(score5.get() == "입력"){
            mListener.onScore5Click()
        }

    }

    fun onScore6Click() {
        if(score6.get() == "입력"){
            mListener.onScore6Click()
        }
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