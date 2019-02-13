package com.project.rankers.retrofit.`interface`

import com.project.rankers.retrofit.models.RankersServerRepo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Multi {
    @FormUrlEncoded
    @POST("record/creator/multi.php")
    fun postMultiCreator(
            @Field("email") email: String?,
            @Field("partner") partner: String?,
            @Field("other") other: String?,
            @Field("otherPartner") otherPartner: String?,
            @Field("date") date: String?,
            @Field("location") location: String?,
            @Field("result") result: String?,
            @Field("win") win: String?,
            @Field("lose") lose: String?
    ): Call<RankersServerRepo>
}