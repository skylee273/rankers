package com.project.rankers.data.remote.response

import com.google.gson.annotations.SerializedName
import com.project.rankers.data.remote.domain.LeagueItem

class LeagueRepo {
    @SerializedName("LEAGUE")
    val items: List<LeagueItem> = listOf()
}