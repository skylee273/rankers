package com.project.rankers.ui.league

import android.util.Log
import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.LeagueItem
import com.project.rankers.data.remote.response.ApplyRepo
import com.project.rankers.data.remote.response.ContestResponse

class LeagueItemViewModel(items: LeagueItem, private val mListener: LeagueItemViewModelListener) {

    val groupNumber: ObservableField<String>
    val player1: ObservableField<String>
    val player2: ObservableField<String>
    val player3: ObservableField<String>
    val player4: ObservableField<String>

    init {
        groupNumber = ObservableField(items.groupNumber.toString() + "조")
        player1 = ObservableField(items.player1)
        player2 = ObservableField(items.player2)
        player3 = ObservableField(items.player3)
        player4 = ObservableField(items.player4)
    }

    fun onPlayer1Click() {
        Log.d("선택","클릭")
        mListener.onPlayer1Click()
    }

    fun onPlayer2Click() {
        mListener.onPlayer2Click()
    }

    fun onPlayer3Click() {
        mListener.onPlayer3Click()
    }

    fun onPlayer4Click() {
        mListener.onPlayer4Click()
    }

    fun onItemClick() {
        mListener.onItemClick()
    }

    interface LeagueItemViewModelListener {
        fun onItemClick()
        fun onPlayer1Click()
        fun onPlayer2Click()
        fun onPlayer3Click()
        fun onPlayer4Click()
    }
}