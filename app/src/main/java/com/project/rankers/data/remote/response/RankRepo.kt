package com.project.rankers.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RankRepo {

    @Expose
    @SerializedName("RANK")
    val items: List<Rank> = listOf()

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is RankRepo) {
            return false
        }

        val that = o as RankRepo?

        return items == that!!.items
    }

    override fun hashCode(): Int {
        return items.hashCode()
    }

    class Rank {
        @Expose
        @SerializedName("RANK_RATE")
        val rankRate : String? = null
        @Expose
        @SerializedName("RANK_NAME")
        val rankName : String? = null
        @Expose
        @SerializedName("RANK_EMAIL")
        val rankEmail : String? = null
        @Expose
        @SerializedName("RANK_TOTAL")
        val rankTotal : String? = null
        @Expose
        @SerializedName("RANK_WIN")
        val rankWin : String? = null
        @Expose
        @SerializedName("RANK_LOSE")
        val rankLose : String? = null

        override fun hashCode(): Int {
            var result = rankRate!!.hashCode()
            result = 31 * result + rankName!!.hashCode()
            result = 31 * result + rankEmail!!.hashCode()
            result = 31 * result + rankTotal!!.hashCode()
            result = 31 * result + rankWin!!.hashCode()
            result = 31 * result + rankLose!!.hashCode()

            return result
        }

    }

}