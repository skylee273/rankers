package com.project.rankers.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ContestResponse {
    // object 중 해당 값이 null일 경우, json으로 만들 필드를 자동 생략해 준다.
    @Expose
    @SerializedName("CONTEST")
    val items: List<Repo> = listOf()

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is ContestResponse) {
            return false
        }

        val that = o as ContestResponse?

        return if (items != null) items == that!!.items else that!!.items == null
    }

    override fun hashCode(): Int {
        return items!!.hashCode()
    }

    class Repo {
        @Expose
        @SerializedName("ID")
        val id: String? = null
        @Expose
        @SerializedName("UID")
        val uid: String? = null
        @Expose
        @SerializedName("TITLE")
        val title: String? = null
        @Expose
        @SerializedName("START")
        val start: String? = null
        @Expose
        @SerializedName("END")
        val end: String? = null
        @Expose
        @SerializedName("TYPE")
        val type: String? = null
        @Expose
        @SerializedName("HOST")
        val host: String = ""
        @Expose
        @SerializedName("LOCATION")
        val location: String? = null
        @Expose
        @SerializedName("DEPART")
        val depart: String? = null

        override fun hashCode(): Int {
            var result = id!!.hashCode()
            result = 31 * result + uid!!.hashCode()
            result = 31 * result + title!!.hashCode()
            result = 31 * result + start!!.hashCode()
            result = 31 * result + end!!.hashCode()
            result = 31 * result + type!!.hashCode()
            result = 31 * result + host!!.hashCode()
            result = 31 * result + location!!.hashCode()
            result = 31 * result + depart!!.hashCode()
            return result
        }

    }
}