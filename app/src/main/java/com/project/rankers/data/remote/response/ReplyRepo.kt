package com.project.rankers.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReplyRepo {

    @Expose
    @SerializedName("REPLY")
    val items: List<Reply> = listOf()

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

    class Reply {
        @Expose
        @SerializedName("REPLY_ID")
        val replyID : String? = null
        @Expose
        @SerializedName("REPLY_UID")
        val replyUID : String? = null
        @Expose
        @SerializedName("REPLY_DATE")
        val replyDate : String? = null
        @Expose
        @SerializedName("REPLY_NAME")
        val replyName : String? = null
        @Expose
        @SerializedName("REPLY_TEXT")
        val replyText : String? = null


        override fun hashCode(): Int {
            var result = replyID!!.hashCode()
            result = 31 * result + replyUID!!.hashCode()
            result = 31 * result + replyDate!!.hashCode()
            result = 31 * result + replyName!!.hashCode()
            result = 31 * result + replyText!!.hashCode()

            return result
        }

    }

}