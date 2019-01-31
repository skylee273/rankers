package com.project.rankers.retrofit.api

import com.project.rankers.retrofit.crater.RankersCreator
import com.project.rankers.retrofit.models.RankersResponseModel
import retrofit2.Call
import retrofit2.http.*
import java.util.*

class RankersApi {
    interface RankersApilmpl {
        @GET("back.php")
        fun getRepoList(): io.reactivex.Observable<RankersResponseModel>
    }

    companion object {
        fun getRepoList(): io.reactivex.Observable<RankersResponseModel> {
            return RankersCreator.create(RankersApilmpl::class.java).getRepoList()
        }

    }
}