package com.project.rankers.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TournamentRepo {
    @Expose
    @SerializedName("TOTAL_COUNT")
    val totalCount: Int = 0
    @Expose
    @SerializedName("TOURNAMENT")
    val items: List<Tournament> = listOf()

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is ContestResponse) {
            return false
        }

        val that = o as ContestResponse?

        return items == that!!.items
    }

    override fun hashCode(): Int {
        return items.hashCode()
    }

    class Tournament {
        @Expose
        @SerializedName("TOURNAMENT_ID")
        val tournamentID : String? = null
        @Expose
        @SerializedName("TOURNAMENT_UID")
        val tournamentUID : String? = null
        @Expose
        @SerializedName("TOURNAMENT_DEPART")
        val tournamentDepart : String? = null
        @Expose
        @SerializedName("TOURNAMENT_PLAYER1")
        val tournamentPlayerOne : String? = null
        @Expose
        @SerializedName("TOURNAMENT_PLAYER2")
        val tournamentPlayerTwo : String? = null
        @Expose
        @SerializedName("TOURNAMENT_SCORE1")
        val tournamentScoreOne : String? = null
        @Expose
        @SerializedName("TOURNAMENT_SCORE2")
        val tournamentScoreTwo : String? = null



        override fun hashCode(): Int {
            var result = tournamentID!!.hashCode()
            result = 31 * result + tournamentUID!!.hashCode()
            result = 31 * result + tournamentDepart!!.hashCode()
            result = 31 * result + tournamentPlayerOne!!.hashCode()
            result = 31 * result + tournamentPlayerTwo!!.hashCode()
            result = 31 * result + tournamentScoreOne!!.hashCode()
            result = 31 * result + tournamentScoreTwo!!.hashCode()

            return result
        }

    }

}