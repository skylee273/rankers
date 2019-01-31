package com.project.rankers.retrofit.`interface`

import com.project.rankers.retrofit.models.RankersServerRepo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Contest {
    @FormUrlEncoded
    @POST("contest/creator.php")
    fun postContestCreator(
            @Field("id") id : String?,
            @Field("name") name : String?,
            @Field("date") date : String?,
            @Field("type") type : String?,
            @Field("host") host : String?,
            @Field("location") location : String?,
            @Field("depart") depart : String?
    ): Call<RankersServerRepo>
}