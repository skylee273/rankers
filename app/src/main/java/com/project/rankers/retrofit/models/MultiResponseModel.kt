package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName

class MultiResponseModel {
    @SerializedName("MULTI")
    val items : List<MultiRepoModel> = listOf()
}