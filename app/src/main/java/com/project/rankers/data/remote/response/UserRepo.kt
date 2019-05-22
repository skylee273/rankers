package com.project.rankers.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRepo {
    @Expose
    @SerializedName("USER")
    val items : List<User> = listOf()

    class User{
        @Expose
        @SerializedName("USER_ID")
        val userID : String? = null
        @Expose
        @SerializedName("USER_ADMIN")
        val userAdmin : String? = null
        @Expose
        @SerializedName("USER_EA")
        val userEmail : String? = null
        @Expose
        @SerializedName("USER_NM")
        val userName : String? = null
        @Expose
        @SerializedName("USER_PH")
        val userPhone : String? = null
        @Expose
        @SerializedName("USER_BT")
        val userBirthday : String? = null


        override fun hashCode(): Int {
            var result = userID!!.hashCode()
            result = 31 * result + userEmail!!.hashCode()
            result = 31 * result + userAdmin!!.hashCode()
            result = 31 * result + userName!!.hashCode()
            result = 31 * result + userPhone!!.hashCode()
            result = 31 * result + userBirthday!!.hashCode()
            return result
        }
    }

}