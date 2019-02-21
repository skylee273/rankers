package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName
import com.project.rankers.retrofit.items.SingleItem

class SingleRepo {
    @SerializedName("SINGLE")
    val items : List<SingleItem> = listOf()
}