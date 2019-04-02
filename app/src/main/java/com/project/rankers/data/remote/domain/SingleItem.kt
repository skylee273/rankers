package com.project.rankers.data.remote.domain

import com.google.gson.annotations.SerializedName

class SingleItem {
    @SerializedName("OTHER")
    val other : String = ""
    @SerializedName("DATE")
    val date : String = ""
    @SerializedName("RESULT")
    val result : String = ""
    @SerializedName("WIN")
    val win : String = ""
    @SerializedName("LOSE")
    val lose : String = ""

}