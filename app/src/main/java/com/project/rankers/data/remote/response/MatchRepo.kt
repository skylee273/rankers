package com.project.rankers.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MatchRepo {

    @Expose
    @SerializedName("MATCH")
    val items: List<Match> = listOf()

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is ContestResponse) {
            return false
        }

        val that = o as ContestResponse?

        return items == that!!.items
    }

    override fun hashCode(): Int {
        return items.hashCode()
    }

    class Match {
        @Expose
        @SerializedName("MATCH_ID")
        val matchID : String? = null
        @Expose
        @SerializedName("MATCH_UID")
        val matchUID : String? = null
        @Expose
        @SerializedName("MATCH_TYPE")
        val matchType : String? = null
        @Expose
        @SerializedName("MATCH_MY")
        val matchMy : String? = null
        @Expose
        @SerializedName("MATCH_PARTNER")
        val matchPartner : String? = null
        @Expose
        @SerializedName("MATCH_OTHER")
        val matchOther : String? = null
        @Expose
        @SerializedName("MATCH_OTHER_PARTNER")
        val matchOtherPartner : String? = null
        @Expose
        @SerializedName("MATCH_DATE")
        val matchDate : String? = null
        @Expose
        @SerializedName("MATCH_RESULT")
        var matchResult : String? = null
        @Expose
        @SerializedName("MATCH_WIN")
        var matchWin : String? = null
        @Expose
        @SerializedName("MATCH_LOSE")
        var matchLose: String? = null


        override fun hashCode(): Int {
            var result = matchID!!.hashCode()
            result = 31 * result + matchUID!!.hashCode()
            result = 31 * result + matchType!!.hashCode()
            result = 31 * result + matchMy!!.hashCode()
            result = 31 * result + matchPartner!!.hashCode()
            result = 31 * result + matchOther!!.hashCode()
            result = 31 * result + matchOtherPartner!!.hashCode()
            result = 31 * result + matchDate!!.hashCode()
            result = 31 * result + matchResult!!.hashCode()
            result = 31 * result + matchLose!!.hashCode()
            result = 31 * result + matchWin!!.hashCode()

            return result
        }

    }

}