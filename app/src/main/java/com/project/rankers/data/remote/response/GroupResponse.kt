package com.project.rankers.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GroupResponse {
    @Expose
    @SerializedName("TOTAL_COUNT")
    val totalCount: Int = 0
    @Expose
    @SerializedName("GROUP")
    val items: List<Group> = listOf()

    class Group {
        @Expose
        @SerializedName("GROUP_ID")
        val groupID : String? = null
        @Expose
        @SerializedName("GROUP_EA")
        val groupEA : String? = null
        @Expose
        @SerializedName("GROUP_DEPART")
        val groupDepart : String? = null
        @Expose
        @SerializedName("GROUP_NUMBER")
        val groupNumber : String? = null
        @Expose
        @SerializedName("GROUP_MEMBER")
        val groupMember : String? = null

        override fun hashCode(): Int {
            var result = groupID!!.hashCode()
            result = 31 * result + groupEA!!.hashCode()
            result = 31 * result + groupDepart!!.hashCode()
            result = 31 * result + groupNumber!!.hashCode()
            result = 31 * result + groupMember!!.hashCode()
            return result
        }

    }
}