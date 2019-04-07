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
        @SerializedName("GROUP_PLAYER1")
        val groupPlayer1 : String? = null
        @Expose
        @SerializedName("GROUP_PLAYER2")
        val groupPlayer2 : String? = null
        @Expose
        @SerializedName("GROUP_PLAYER3")
        val groupPlayer3 : String? = null
        @Expose
        @SerializedName("GROUP_PLAYER4")
        val groupPlayer4 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE1")
        val groupScore1 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE2")
        val groupScore2 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE3")
        val groupScore3 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE4")
        val groupScore4 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE5")
        val groupScore5 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE6")
        val groupScore6 : String? = null

        override fun hashCode(): Int {
            var result = groupID!!.hashCode()
            result = 31 * result + groupEA!!.hashCode()
            result = 31 * result + groupDepart!!.hashCode()
            result = 31 * result + groupNumber!!.hashCode()
            result = 31 * result + groupPlayer1!!.hashCode()
            result = 31 * result + groupPlayer2!!.hashCode()
            result = 31 * result + groupPlayer3!!.hashCode()
            result = 31 * result + groupPlayer4!!.hashCode()
            result = 31 * result + groupScore1!!.hashCode()
            result = 31 * result + groupScore2!!.hashCode()
            result = 31 * result + groupScore3!!.hashCode()
            result = 31 * result + groupScore4!!.hashCode()
            result = 31 * result + groupScore5!!.hashCode()
            result = 31 * result + groupScore6!!.hashCode()

            return result
        }

    }
}