package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName

class SingleResponseModel {
    @SerializedName("SINGLE")
    val items : List<SingleRepoModel> = listOf()
}