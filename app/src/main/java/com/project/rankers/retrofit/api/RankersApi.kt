package com.project.rankers.retrofit.api

import com.project.rankers.retrofit.crater.RankersCreator
import com.project.rankers.retrofit.models.MultiResponseModel
import com.project.rankers.retrofit.models.RankersResponseModel
import com.project.rankers.retrofit.models.SingleResponseModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import java.util.*

class RankersApi {
    interface RankersApilmpl {
        @GET("record/get/single.php")
        fun getSingleRepoList(
                @Query("email") email: String
        ) : Observable<SingleResponseModel>
        @GET("record/get/multi.php")
        fun getMultiRepoList(
                @Query("email") email: String
        ) : Observable<MultiResponseModel>
    }


    companion object {
        fun getSingleRepoList(email : String) : Observable<SingleResponseModel> {
            return RankersCreator.create(RankersApilmpl::class.java).getSingleRepoList(email)
        }
        fun getMultiRepoList(email : String) : Observable<MultiResponseModel> {
            return RankersCreator.create(RankersApilmpl::class.java).getMultiRepoList(email)
        }
    }
}