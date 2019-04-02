package com.project.rankers.data.remote.response

import com.google.gson.annotations.SerializedName
import com.project.rankers.data.remote.domain.MultiItem

class MultiRepo {
    @SerializedName("MULTI")
    val items : List<MultiItem> = listOf()
}