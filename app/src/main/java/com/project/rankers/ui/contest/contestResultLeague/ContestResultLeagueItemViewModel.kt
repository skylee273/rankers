package com.project.rankers.ui.contest.contestResultLeague

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.project.rankers.data.remote.response.GroupResponse

class ContestResultLeagueItemViewModel(group: GroupResponse.Group, private val mListener: ContestResultLeagueItemViewModelListener) {


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

    val isPlayerOneWin = ObservableBoolean()
    val isPlayerTwoWin = ObservableBoolean()
    val isPlayerThreeWin = ObservableBoolean()
    val isPlayerFourWin = ObservableBoolean()

    init {
        player1 = ObservableField(group.groupPlayer1!!)
        player2 = ObservableField(group.groupPlayer2!!)
        player3 = ObservableField(group.groupPlayer3!!)
        player4 = ObservableField(group.groupPlayer4!!)
        player5 = ObservableField(group.groupPlayer1)
        player6 = ObservableField(group.groupPlayer2)
        player7 = ObservableField(group.groupPlayer3)
        player8 = ObservableField(group.groupPlayer4)

        score1 =ObservableField(isMatch(group.groupScore1!!))
        score2 =ObservableField(isMatch(group.groupScore2!!))
        score3 =ObservableField(isMatch(group.groupScore3!!))
        score4 =ObservableField(isMatch(group.groupScore4!!))
        score5 =ObservableField(isMatch(group.groupScore5!!))
        score6 =ObservableField(isMatch(group.groupScore6!!))

        score7 =ObservableField (isMatch(group.groupScore1!!.reversed()))
        score8 =ObservableField (isMatch(group.groupScore2!!.reversed()))
        score9 =ObservableField (isMatch(group.groupScore3!!.reversed()))
        score10 =ObservableField(isMatch(group.groupScore4!!.reversed()))
        score11 =ObservableField(isMatch(group.groupScore5!!.reversed()))
        score12 =ObservableField(isMatch(group.groupScore6!!.reversed()))


        gain1 = ObservableField((group.groupGain1!!))
        gain2 = ObservableField((group.groupGain2!!))
        gain3 = ObservableField((group.groupGain3!!))
        gain4 = ObservableField((group.groupGain4!!))


        totalScore1 = ObservableField((group.groupTotal1!!))
        totalScore2 = ObservableField((group.groupTotal2!!))
        totalScore3 = ObservableField((group.groupTotal3!!))
        totalScore4 = ObservableField((group.groupTotal4!!))

        rank1 = ObservableField((group.groupRank1!!))
        rank2 = ObservableField((group.groupRank2!!))
        rank3 = ObservableField((group.groupRank3!!))
        rank4 = ObservableField((group.groupRank4!!))

        if(group.groupRank1 == "1" || group.groupRank1 == "2"){
            setPlayerOne(true)
        } else{
            setPlayerOne(false)
        }

        if(group.groupRank2 == "1" || group.groupRank2 == "2"){
            setPlayerTwo(true)
        } else{
            setPlayerTwo(false)
        }

        if(group.groupRank3 == "1" || group.groupRank3 == "2"){
            setPlayerThree(true)
        } else{
            setPlayerThree(false)
        }

        if(group.groupRank4 == "1" || group.groupRank4 == "2"){
            setPlayerFour(true)
        } else{
            setPlayerFour(false)
        }

        number = ObservableField(group.groupNumber!! + "조 매치")
    }

    private fun setPlayerOne(isWin: Boolean) {
        this.isPlayerOneWin.set(isWin)
    }
    private fun setPlayerTwo(isWin: Boolean) {
        this.isPlayerTwoWin.set(isWin)
    }
    private fun setPlayerThree(isWin: Boolean) {
        this.isPlayerThreeWin.set(isWin)
    }
    private fun setPlayerFour(isWin: Boolean) {
        this.isPlayerFourWin.set(isWin)
    }


    private fun isMatch(str: String): String {
        return if (str == "0") {
            "경기전"
        } else {
            str
        }
    }

    interface ContestResultLeagueItemViewModelListener
}