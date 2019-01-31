package com.project.rankers.retrofit.`interface`

import com.project.rankers.retrofit.models.RankersServerRepo
import retrofit2.Call
import retrofit2.http.*

interface RankersUser {
    @FormUrlEncoded
    @POST("user/creator.php")
    fun postUserCreator(
            @Field("email") email : String?,
            @Field("nickName") nickName : String?
    ): Call<RankersServerRepo>
    @GET("user/find.php")
    fun getID(
            @Query("email") email: String?
    ): Call<RankersServerRepo>
}