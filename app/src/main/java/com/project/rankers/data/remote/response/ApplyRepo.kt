package com.project.rankers.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApplyRepo {
    @Expose
    @SerializedName("TOTAL_COUNT")
    val totalCount: Int = 0
    @Expose
    @SerializedName("APPLY")
    val items : List<Apply> = listOf()

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

    class Apply{
        @Expose
        @SerializedName("APPLY_ID")
        val applyID : String? = null
        @Expose
        @SerializedName("APPLY_UID")
        val applyUID : String? = null
        @Expose
        @SerializedName("APPLY_DEPART")
        val applyDepart : String? = null
        @Expose
        @SerializedName("APPLY_TYPE")
        val applyType : String? = null
        @Expose
        @SerializedName("APPLY_NAME")
        val applyName : String? = null
        @Expose
        @SerializedName("APPLY_PH")
        val applyPhone : String? = null
        @Expose
        @SerializedName("APPLY_PARTNER")
        val applyPartner : String? = null
        @Expose
        @SerializedName("APPLY_PARTNER_PH")
        val applyPartnerPhone : String? = null

        override fun hashCode(): Int {
            var result = applyID!!.hashCode()
            result = 31 * result + applyUID!!.hashCode()
            result = 31 * result + applyDepart!!.hashCode()
            result = 31 * result + applyType!!.hashCode()
            result = 31 * result + applyName!!.hashCode()
            result = 31 * result + applyPhone!!.hashCode()
            result = 31 * result + applyPartner!!.hashCode()
            result = 31 * result + applyPartnerPhone!!.hashCode()
            return result
        }


    }


}