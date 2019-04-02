package com.project.rankers.data.remote.response

import com.google.gson.annotations.SerializedName
import com.project.rankers.data.remote.domain.ApplyItem

class ApplyRepo {
    @SerializedName("TOTAL_COUNT")
    val totalCount: Int = 0
    @SerializedName("APPLY")
    val items : List<ApplyItem> = listOf()
}