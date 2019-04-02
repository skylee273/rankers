package com.project.rankers.data.remote.response

import com.google.gson.annotations.SerializedName

class ServerRepo {
    @SerializedName("success")
    private val success: Boolean = false
    fun getSuccess(): Boolean {
        return success
    }
}