package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName
import com.project.rankers.retrofit.items.MultiItem

class MultiRepo {
    @SerializedName("MULTI")
    val items : List<MultiItem> = listOf()
}