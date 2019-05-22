package com.project.rankers.ui.contest.tournament

import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.Tournament

class TournamentItemViewModel(tournament: Tournament, private val mListener: TournamentItemViewModel.TournamentItemViewModelListener) {
    val teamOneName: ObservableField<String>
    val teamTwoName: ObservableField<String>
    var teamOneRank: ObservableField<String>
    var teamTwoRank: ObservableField<String>
    val number: ObservableField<String>

    init {
        teamOneName = ObservableField(tournament.teamOneName)
        teamTwoName = ObservableField(tournament.teamTwoName)
        teamOneRank = ObservableField(tournament.teamOneRank + "위")
        teamTwoRank = if(teamTwoName.toString() == ""){
            ObservableField(tournament.teamTwoRank)
        }else{
            ObservableField(tournament.teamTwoRank + "위")
        }

        number = ObservableField(tournament.number + "조")
    }


    fun onTeamOneClick(){
        mListener.onTeamOneClick()
    }
    fun onTeamTwoClick(){
        mListener.onTeamTwoClick()
    }
    fun onItemClick() {
        mListener.onItemClick()
    }

    interface TournamentItemViewModelListener {
        fun onItemClick()
        fun onTeamOneClick()
        fun onTeamTwoClick()
    }


}