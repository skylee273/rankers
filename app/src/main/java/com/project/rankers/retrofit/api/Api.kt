package com.project.rankers.retrofit.api

import com.project.rankers.retrofit.crater.RetrofitCreator
import com.project.rankers.retrofit.models.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

class Api {
    interface ApiImpl {

        @GET("record/get/single.php")
        fun getSingleRepoList(
                @Query("email") email: String
        ) : Observable<SingleRepo>

        @GET("record/get/multi.php")
        fun getMultiRepoList(
                @Query("email") email: String
        ) : Observable<MultiRepo>

        @GET("contest/list.php")
        fun getContestList() : Observable<ContestRepo>

        @GET("user/find.php")
        fun getID(
                @Query("email") email: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("record/creator/single.php")

        fun postSingleCreator(
                @Field("email") email: String?,
                @Field("other") other: String?,
                @Field("date") date: String?,
                @Field("result") result: String?,
                @Field("win") win: String?,
                @Field("lose") lose: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("record/creator/multi.php")
        fun postMultiCreator(
                @Field("email") email: String?,
                @Field("partner") partner: String?,
                @Field("other") other: String?,
                @Field("otherPartner") otherPartner: String?,
                @Field("date") date: String?,
                @Field("result") result: String?,
                @Field("win") win: String?,
                @Field("lose") lose: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("contest/creator.php")
        fun postContestCreator(
                @Field("id") id: String?,
                @Field("name") name: String?,
                @Field("start") date: String?,
                @Field("end") end: String?,
                @Field("type") type: String?,
                @Field("host") host: String?,
                @Field("location") location: String?,
                @Field("depart") depart: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("user/creator.php")
        fun postUserCreator(
                @Field("email") email : String?,
                @Field("nickName") nickName : String?
        ): Observable<ServerRepo>

    }


    companion object {
        fun getID(email: String?) : Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getID(email)
        }
        fun getSingleRepoList(email : String) : Observable<SingleRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getSingleRepoList(email)
        }
        fun getMultiRepoList(email : String) : Observable<MultiRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getMultiRepoList(email)
        }
        fun getContestList() : Observable<ContestRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).getContestList()
        }
        fun postUserCreator(email: String?, nickName: String?) : Observable<ServerRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).postUserCreator(email, nickName)
        }
        fun postSingleCreator(email: String?, other: String?, date: String?, result: String?, win: String?, lose: String?) : Observable<ServerRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).postSingleCreator(email, other, date, result, win, lose)
        }
        fun postMultiCreator(email: String?, partner: String?, other: String?, otherPartner: String?, date: String?, result: String?, win: String?, lose: String?) : Observable<ServerRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).postMultiCreator(email, partner, other, otherPartner, date, result, win, lose)
        }
        fun postContestCreator(id: String?, name: String?, date: String?, end: String?, type: String?, host: String?, location: String?, depart: String?) : Observable<ServerRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).postContestCreator(id, name, date, end, type, host, location, depart)
        }
    }
}