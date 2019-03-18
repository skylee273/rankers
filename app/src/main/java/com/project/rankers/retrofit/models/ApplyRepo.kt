package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName
import com.project.rankers.retrofit.items.ApplyItem
import com.project.rankers.retrofit.items.ContestItem

class ApplyRepo {
    @SerializedName("TOTAL_COUNT")
    val totalCount: Int = 0
    @SerializedName("APPLY")
    val items : List<ApplyItem> = listOf()
}