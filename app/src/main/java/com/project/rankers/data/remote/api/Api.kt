package com.project.rankers.data.remote.api

import com.project.rankers.data.remote.response.*
import com.project.rankers.data.remote.crater.RetrofitCreator
import io.reactivex.Observable
import retrofit2.http.*

class Api {
    interface ApiImpl {

        @GET("record/get/single.php")
        fun getSingleRepoList(
                @Query("email") email: String
        ): Observable<SingleRepo>

        @GET("record/get/multi.php")
        fun getMultiRepoList(
                @Query("email") email: String
        ): Observable<MultiRepo>

        @GET("contest/list.php")
        fun getContestList(): Observable<ContestResponse>

        @GET("user/find.php")
        fun getID(
                @Query("email") email: String?
        ): Observable<ServerRepo>

        @GET("contest/listMy.php")
        fun getOperationList(
                @Query("email") email: String?
        ): Observable<ContestResponse>

        @GET("contest/applyList.php")
        fun getApplyList(
                @Query("id") id: String?,
                @Query("depart") depart: String?
        ): Observable<ApplyRepo>

        @GET("contest/leagueList.php")
        fun getLeagueList(
                @Query("id") id: String?,
                @Query("depart") depart: String?
        ): Observable<LeagueRepo>

        @GET("contest/groupList.php")
        fun getGroupList(
                @Query("id") id: String?,
                @Query("depart") depart: String?
        ): Observable<GroupResponse>

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
        @POST("contest/uploadLeague.php")
        fun postGroupCreator(
                @Field("id") id: String?,
                @Field("email") email: String?,
                @Field("depart") depart: String?,
                @Field("number") number: Int?,
                @Field("groupMember") groupMember: String?
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
        @POST("contest/application.php")
        fun postApplicationCreator(
                @Field("id") id: String?,
                @Field("email") email: String?,
                @Field("depart") depart: String?,
                @Field("type") type: Int?,
                @Field("name") name: String?,
                @Field("phone") phone: String?,
                @Field("partner") end: String?,
                @Field("partnerPhone") partnerPhone: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("user/creator.php")
        fun postUserCreator(
                @Field("email") email: String?,
                @Field("nickName") nickName: String?
        ): Observable<ServerRepo>

    }


    companion object {
        fun getGroupList(id: String?, depart: String?) : Observable<GroupResponse> {
            return RetrofitCreator.create(ApiImpl::class.java).getGroupList(id, depart)
        }
        fun getLeagueList(id: String?, depart: String?) : Observable<LeagueRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getLeagueList(id, depart)
        }
        fun getApplyList(id: String?, depart: String?): Observable<ApplyRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getApplyList(id, depart)
        }

        fun getOperationList(email: String?): Observable<ContestResponse> {
            return RetrofitCreator.create(ApiImpl::class.java).getOperationList(email)
        }

        fun getID(email: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getID(email)
        }

        fun getSingleRepoList(email: String): Observable<SingleRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getSingleRepoList(email)
        }

        fun getMultiRepoList(email: String): Observable<MultiRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getMultiRepoList(email)
        }

        fun getContestList(): Observable<ContestResponse> {
            return RetrofitCreator.create(ApiImpl::class.java).getContestList()
        }

        fun postGroupCreator(id: String?, email: String?, depart: String?, number: Int?, groupMember: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postGroupCreator(id, email,depart, number, groupMember)
        }

        fun postUserCreator(email: String?, nickName: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postUserCreator(email, nickName)
        }

        fun postSingleCreator(email: String?, other: String?, date: String?, result: String?, win: String?, lose: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postSingleCreator(email, other, date, result, win, lose)
        }

        fun postMultiCreator(email: String?, partner: String?, other: String?, otherPartner: String?, date: String?, result: String?, win: String?, lose: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postMultiCreator(email, partner, other, otherPartner, date, result, win, lose)
        }

        fun postContestCreator(id: String?, name: String?, date: String?, end: String?, type: String?, host: String?, location: String?, depart: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postContestCreator(id, name, date, end, type, host, location, depart)
        }

        fun postApplicationCreator(id: String?, email: String?, depart: String?, type: Int?, name: String?, phone: String?, partner: String?, partnerPhone: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postApplicationCreator(id, email, depart, type, name, phone, partner, partnerPhone)
        }
    }
}