package com.project.rankers.data.remote.domain

import com.google.gson.annotations.SerializedName

class LeagueItem {
    @SerializedName("LEAGUE_ID")
    val leagueID : String = ""
    @SerializedName("LEAGUE_EA")
    val leagueEA : String = ""
    @SerializedName("LEAGUE_DEPART")
    val leagueDepart : String = ""
    @SerializedName("LEAGUE_COUNT")
    val leagueCount : String = ""
    @SerializedName("LEAGUE_MEMBER")
    val leagueMember : String = ""
    @SerializedName("LEAGUE_SCORE")
    val leagueScore : String = ""
}