package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName
import com.project.rankers.retrofit.items.GroupItem
import com.project.rankers.retrofit.items.LeagueItem

class GroupRepo {
    @SerializedName("GROUP")
    val items: List<GroupItem> = listOf()

}