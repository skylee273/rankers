package com.project.rankers.ui.tournament

import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.Tournament
import com.project.rankers.data.remote.response.GroupResponse

class TournamentItemViewModel(tournament: Tournament, private val mListener: TournamentItemViewModel.TournamentItemViewModelListener) {
    val teamOneName: ObservableField<String>
    val teamTwoName: ObservableField<String>
    var teamOneRank: ObservableField<String>
    var teamTwoRank: ObservableField<String>


    val depart: ObservableField<String>


    init {
        teamOneName = ObservableField(tournament.teamOneName)
        teamTwoName = ObservableField(tournament.teamTwoName)
        teamOneRank = ObservableField(tournament.teamOneRank + "위")
        teamTwoRank = ObservableField(tournament.teamTwoRank + "위")
        depart = ObservableField(tournament.departName)
    }




    fun onItemClick() {
        mListener.onItemClick()
    }

    interface TournamentItemViewModelListener {
        fun onItemClick()

    }


}