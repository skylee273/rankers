package com.project.rankers.data.remote.response

import com.google.gson.annotations.SerializedName
import com.project.rankers.data.remote.domain.SingleItem

class SingleRepo {
    @SerializedName("SINGLE")
    val items : List<SingleItem> = listOf()
}