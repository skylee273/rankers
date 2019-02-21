package com.project.rankers.retrofit.models

import com.google.gson.annotations.SerializedName

class ServerRepo {
    @SerializedName("success")
    private val success: Boolean = false
    fun getSuccess(): Boolean {
        return success
    }
}