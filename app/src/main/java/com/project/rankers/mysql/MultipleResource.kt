package com.project.rankers.mysql

import com.google.gson.annotations.SerializedName

class MultipleResource {

    class UserInfo{
        @SerializedName("id")
        var id : String? = null

        @SerializedName("name")
        var name : String? = null
    }
}