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
        @SerializedName("GROUP_UID")
        val groupUID : String? = null
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
        var groupScore1 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE2")
        var groupScore2 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE3")
        var groupScore3 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE4")
        var groupScore4 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE5")
        var groupScore5 : String? = null
        @Expose
        @SerializedName("GROUP_SCORE6")
        var groupScore6 : String? = null
        @Expose
        @SerializedName("GROUP_GAIN1")
        var groupGain1 : String? = null
        @Expose
        @SerializedName("GROUP_GAIN2")
        var groupGain2 : String? = null
        @Expose
        @SerializedName("GROUP_GAIN3")
        var groupGain3 : String? = null
        @Expose
        @SerializedName("GROUP_GAIN4")
        var groupGain4 : String? = null

        @Expose
        @SerializedName("GROUP_TOTAL1")
        var groupTotal1 : String? = null
        @Expose
        @SerializedName("GROUP_TOTAL2")
        var groupTotal2 : String? = null
        @Expose
        @SerializedName("GROUP_TOTAL3")
        var groupTotal3 : String? = null
        @Expose
        @SerializedName("GROUP_TOTAL4")
        var groupTotal4 : String? = null
        @Expose
        @SerializedName("GROUP_RANK1")
        var groupRank1 : String? = null
        @Expose
        @SerializedName("GROUP_RANK2")
        var groupRank2 : String? = null
        @Expose
        @SerializedName("GROUP_RANK3")
        var groupRank3 : String? = null
        @Expose
        @SerializedName("GROUP_RANK4")
        var groupRank4 : String? = null

        override fun hashCode(): Int {
            var result = groupID!!.hashCode()
            result = 31 * result + groupUID!!.hashCode()
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
            result = 31 * result + groupGain1!!.hashCode()
            result = 31 * result + groupGain2!!.hashCode()
            result = 31 * result + groupGain3!!.hashCode()
            result = 31 * result + groupGain4!!.hashCode()
            result = 31 * result + groupTotal1!!.hashCode()
            result = 31 * result + groupTotal2!!.hashCode()
            result = 31 * result + groupTotal3!!.hashCode()
            result = 31 * result + groupTotal4!!.hashCode()
            result = 31 * result + groupRank1!!.hashCode()
            result = 31 * result + groupRank2!!.hashCode()
            result = 31 * result + groupRank3!!.hashCode()
            result = 31 * result + groupRank4!!.hashCode()

            return result
        }

    }
}