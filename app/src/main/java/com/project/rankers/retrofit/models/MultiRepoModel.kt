package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName

class MultiRepoModel {
    @SerializedName("PARTNER")
    val partner : String = ""
    @SerializedName("OTHER")
    val other : String = ""
    @SerializedName("OTHERPARTNER")
    val otherpartner : String = ""
    @SerializedName("DATE")
    val date : String = ""
    @SerializedName("LOCATION")
    val location : String = ""
    @SerializedName("RESULT")
    val result : String = ""
    @SerializedName("WIN")
    val win : String = ""
    @SerializedName("LOSE")
    val lose : String = ""
}