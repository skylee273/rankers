package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName
import com.project.rankers.retrofit.items.ContestItem
import com.project.rankers.retrofit.items.LeagueItem

class LeagueRepo {
    @SerializedName("LEAGUE")
    val items: List<LeagueItem> = listOf()
}