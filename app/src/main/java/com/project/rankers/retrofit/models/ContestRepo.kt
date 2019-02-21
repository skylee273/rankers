package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName
import com.project.rankers.retrofit.items.ContestItem

class ContestRepo {
    @SerializedName("CONTEST")
    val items : List<ContestItem> = listOf()
}