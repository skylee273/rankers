package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName

class RankersResponseModel {
    @SerializedName("USER")
    val items : List<RankersRepoModel> = listOf()
}