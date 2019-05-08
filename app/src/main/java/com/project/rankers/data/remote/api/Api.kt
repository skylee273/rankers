package com.project.rankers.data.remote.api

import com.project.rankers.data.remote.response.*
import com.project.rankers.data.remote.crater.RetrofitCreator
import io.reactivex.Observable
import retrofit2.http.*

class Api {
    interface ApiImpl {


        @GET("match/matchList.php")
        fun getMatchList(
                @Query("uid") uid: String
        ): Observable<MatchRepo>

        @GET("record/get/single.php")
        fun getTournamentList(
                @Query("email") email: String
        ): Observable<TournamentRepo>

        @GET("user/loadAllUser.php")
        fun getUserAll(
        ): Observable<UserRepo>

        @GET("user/loadAllByIds.php")
        fun getUserAllByIds(
                @Query("email") email: String
        ): Observable<UserRepo>

        @GET("record/get/single.php")
        fun getSingleRepoList(
                @Query("id") id: String
        ): Observable<SingleRepo>

        @GET("record/get/multi.php")
        fun getMultiRepoList(
                @Query("id") id: String
        ): Observable<MultiRepo>

        @GET("contest/contestList.php")
        fun getContestList(): Observable<ContestResponse>

        @GET("user/find.php")
        fun getID(
                @Query("email") email: String?
        ): Observable<ServerRepo>

        @GET("contest/contestMyList.php")
        fun getOperationList(
                @Query("uid") uid: String?
        ): Observable<ContestResponse>

        @GET("contest/applyList.php")
        fun getApplyList(
                @Query("id") id: String?,
                @Query("depart") depart: String?
        ): Observable<ApplyRepo>

        @GET("contest/groupList.php")
        fun getGroupList(
                @Query("id") id: String?,
                @Query("depart") depart: String?
        ): Observable<GroupResponse>

        @GET("contest/tournamentList.php")
        fun getTounamentList(
                @Query("id") id: String?,
                @Query("depart") depart: String?
        ): Observable<TournamentRepo>

        @FormUrlEncoded
        @POST("match/matchCreator.php")
        fun postMatchCreator(
                @Field("uid") uid: String?,
                @Field("type") type: String?,
                @Field("my") my: String?,
                @Field("partner") partner: String?,
                @Field("other") other: String?,
                @Field("otherPartner") otherPartner: String?,
                @Field("date") date: String?,
                @Field("result") result: String?,
                @Field("win") win: String?,
                @Field("lose") lose: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("record/creator/single.php")
        fun postSingleCreator(
                @Field("id") id: String?,
                @Field("other") other: String?,
                @Field("date") date: String?,
                @Field("result") result: String?,
                @Field("win") win: String?,
                @Field("lose") lose: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("record/creator/multi.php")
        fun postMultiCreator(
                @Field("id") id: String?,
                @Field("partner") partner: String?,
                @Field("other") other: String?,
                @Field("otherPartner") otherPartner: String?,
                @Field("date") date: String?,
                @Field("result") result: String?,
                @Field("win") win: String?,
                @Field("lose") lose: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("contest/groupCreator.php")
        fun postGroupCreator(
                @Field("id") id: String?,
                @Field("uid") uid: String?,
                @Field("depart") depart: String?,
                @Field("number") number: Int?,
                @Field("player1") player1: String?,
                @Field("player2") player2: String?,
                @Field("player3") player3: String?,
                @Field("player4") player4: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("contest/tournamentCreator.php")
        fun postTournamentCreator(
                @Field("id") id: String?,
                @Field("uid") uid: String?,
                @Field("type") type: String?,
                @Field("depart") depart: String?,
                @Field("player1") player1: String?,
                @Field("player2") player2: String?,
                @Field("score1") score1: String?,
                @Field("score2") score2: String?
        ): Observable<ServerRepo>


        @FormUrlEncoded
        @POST("contest/contestCreator.php")
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
                @Field("uid") uid: String?,
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
                @Field("nickName") nickName: String?,
                @Field("phone") phone: String?,
                @Field("birthday") birthday: String?

        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("contest/groupUpdate.php")
        fun postUpdateGroup(
                @Field("GROUP_ID") groupID: String?,
                @Field("GROUP_DEPART") groupDepart: String?,
                @Field("GROUP_NUMBER") groupNumber: String?,
                @Field("GROUP_SCORE1") groupScore1: String?,
                @Field("GROUP_SCORE2") groupScore2: String?,
                @Field("GROUP_SCORE3") groupScore3: String?,
                @Field("GROUP_SCORE4") groupScore4: String?,
                @Field("GROUP_SCORE5") groupScore5: String?,
                @Field("GROUP_SCORE6") groupScore6: String?,
                @Field("GROUP_TOTAL1") groupTotal1: String?,
                @Field("GROUP_TOTAL2") groupTotal2: String?,
                @Field("GROUP_TOTAL3") groupTotal3: String?,
                @Field("GROUP_TOTAL4") groupTotal4: String?,
                @Field("GROUP_RANK1") groupRank1: String?,
                @Field("GROUP_RANK2") groupRank2: String?,
                @Field("GROUP_RANK3") groupRank3: String?,
                @Field("GROUP_RANK4") groupRank4: String?
        ): Observable<ServerRepo>
    }


    companion object {

        fun getMatchList(uid: String?): Observable<MatchRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getMatchList(uid!!)
        }

        fun getUserAll() : Observable<UserRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getUserAll()
        }
        fun getUserAllByIds(email: String?): Observable<UserRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getUserAllByIds(email!!)
        }

        fun getGroupList(id: String?, depart: String?): Observable<GroupResponse> {
            return RetrofitCreator.create(ApiImpl::class.java).getGroupList(id, depart)
        }

        fun getApplyList(id: String?, depart: String?): Observable<ApplyRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getApplyList(id, depart)
        }

        fun getOperationList(uid: String?): Observable<ContestResponse> {
            return RetrofitCreator.create(ApiImpl::class.java).getOperationList(uid)
        }

        fun getID(email: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getID(email)
        }

        fun getSingleRepoList(id: String): Observable<SingleRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getSingleRepoList(id)
        }

        fun getMultiRepoList(id: String): Observable<MultiRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getMultiRepoList(id)
        }

        fun getTounamentList(id: String?, depart: String?) : Observable<TournamentRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).getTounamentList(id, depart)
        }
        fun getContestList(): Observable<ContestResponse> {
            return RetrofitCreator.create(ApiImpl::class.java).getContestList()
        }

        fun postMatchCreator(uid: String?, type: String?, my: String?, partner: String?, other: String?, otherPartner: String?, date: String?, result: String?, win: String?, lose: String?) : Observable<ServerRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).postMatchCreator(uid, type, my, partner, other, otherPartner, date, result, win, lose)
        }

        fun postTournamentCreator(id: String?, uid: String?, type: String?, depart: String?, player1: String?, player2: String?, score1: String?, score2: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postTournamentCreator(id, uid, type, depart, player1, player2, score1, score2)
        }
        fun postGroupCreator(id: String?, uid: String?, depart: String?, number: Int?, player1: String?, player2: String?, player3: String?, player4: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postGroupCreator(id, uid, depart, number, player1, player2, player3, player4)
        }

        fun postUserCreator(email: String?, nickName: String?, phone: String?, birthday: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postUserCreator(email, nickName, phone, birthday)
        }

        fun postSingleCreator(id: String?, other: String?, date: String?, result: String?, win: String?, lose: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postSingleCreator(id, other, date, result, win, lose)
        }

        fun postMultiCreator(id: String?, partner: String?, other: String?, otherPartner: String?, date: String?, result: String?, win: String?, lose: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postMultiCreator(id, partner, other, otherPartner, date, result, win, lose)
        }

        fun postContestCreator(id: String?, name: String?, date: String?, end: String?, type: String?, host: String?, location: String?, depart: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postContestCreator(id, name, date, end, type, host, location, depart)
        }

        fun postApplicationCreator(id: String?, uid: String?, depart: String?, type: Int?, name: String?, phone: String?, partner: String?, partnerPhone: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postApplicationCreator(id, uid, depart, type, name, phone, partner, partnerPhone)
        }

        fun postUpdateGroup(groupID: String?, groupDepart: String?, groupNumber: String?, groupScore1: String?, groupScore2: String?, groupScore3: String?, groupScore4: String?, groupScore5: String?, groupScore6: String?, groupTotal1: String?, groupTotal2: String?, groupTotal3: String?, groupTotal4: String?,
                            groupRank1: String?, groupRank2: String?, groupRank3: String?, groupRank4: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postUpdateGroup(groupID, groupDepart, groupNumber, groupScore1, groupScore2, groupScore3, groupScore4,
                    groupScore5, groupScore6, groupTotal1, groupTotal2, groupTotal3, groupTotal4, groupRank1, groupRank2, groupRank3, groupRank4)
        }
    }
}