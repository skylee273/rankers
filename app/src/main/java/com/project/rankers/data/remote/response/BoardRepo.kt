package com.project.rankers.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BoardRepo {

    @Expose
    @SerializedName("BOARD")
    val items: List<Board> = listOf()

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o !is ContestResponse) {
            return false
        }

        val that = o as BoardRepo?

        return items == that!!.items
    }

    override fun hashCode(): Int {
        return items.hashCode()
    }

    class Board {
        @Expose
        @SerializedName("BOARD_ID")
        val boardID : String? = null
        @Expose
        @SerializedName("BOARD_UID")
        val boardUID : String? = null
        @Expose
        @SerializedName("BOARD_NAME")
        val boardName : String? = null
        @Expose
        @SerializedName("BOARD_DATE")
        val boardDate : String? = null
        @Expose
        @SerializedName("BOARD_TITLE")
        val boardTitle : String? = null
        @Expose
        @SerializedName("BOARD_TEXT")
        val boardText : String? = null
        @SerializedName("BOARD_VIEW_CNT")
        val boardViewCnt : String? = null
        @SerializedName("BOARD_REPLY_CNT")
        val boardReplyCnt : String? = null

        override fun hashCode(): Int {
            var result = boardID!!.hashCode()
            result = 31 * result + boardUID!!.hashCode()
            result = 31 * result + boardName!!.hashCode()
            result = 31 * result + boardDate!!.hashCode()
            result = 31 * result + boardTitle!!.hashCode()
            result = 31 * result + boardText!!.hashCode()
            result = 31 * result + boardViewCnt!!.hashCode()
            result = 31 * result + boardReplyCnt!!.hashCode()


            return result
        }

    }

}