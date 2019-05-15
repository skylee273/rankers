package com.project.rankers.data.remote.api

import com.project.rankers.data.remote.crater.RetrofitCreator
import com.project.rankers.data.remote.response.*
import io.reactivex.Observable
import retrofit2.http.*

class Api {
    interface ApiImpl {

        @GET("match/matchList.php")
        fun getMatchList(
                @Query("uid") uid: String
        ): Observable<MatchRepo>

        @GET("user/loadAllUser.php")
        fun getUserAll(
        ): Observable<UserRepo>

        @GET("user/loadAllByIds.php")
        fun getUserAllByIds(
                @Query("email") email: String
        ): Observable<UserRepo>


        @GET("contest/loadAllByIds.php")
        fun getContestAllByIds(
                @Query("id") id: String
        ): Observable<ContestResponse>


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

        @GET("contest/groupLoadAllByIds.php")
        fun getGroupAllByIds(
                @Query("id") id: String?,
                @Query("depart") depart: String?
        ): Observable<ServerRepo>


        @GET("contest/tournamentList.php")
        fun getTournamentList(
                @Query("id") id: String?,
                @Query("depart") depart: String?
        ): Observable<TournamentRepo>

        @GET("contest/tournamentLoadAllByIds.php")
        fun getTournamentAllByIds(
                @Query("id") id: String?,
                @Query("depart") depart: String?
        ): Observable<ServerRepo>

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
                @Field("round") round: String?,
                @Field("number") number: Int?,
                @Field("depart") depart: String?,
                @Field("player1") player1: String?,
                @Field("player2") player2: String?,
                @Field("score1") score1: String?,
                @Field("score2") score2: String?
        ): Observable<ServerRepo>

        @FormUrlEncoded
        @POST("contest/tournamentUpdate.php")
        fun postUpdateTournament(
                @Field("TOURNAMENT_ID") id: String?,
                @Field("TOURNAMENT_UID") uid: String?,
                @Field("TOURNAMENT_TYPE") type: String?,
                @Field("TOURNAMENT_ROUND") round: String?,
                @Field("TOURNAMENT_NUMBER") number: Int?,
                @Field("TOURNAMENT_DEPART") depart: String?,
                @Field("TOURNAMENT_PLAYER1") player1: String?,
                @Field("TOURNAMENT_PLAYER2") player2: String?,
                @Field("TOURNAMENT_SCORE1") score1: String?,
                @Field("TOURNAMENT_SCORE2") score2: String?
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
                @Field("GROUP_GAIN1") groupGain1: String?,
                @Field("GROUP_GAIN2") groupGain2: String?,
                @Field("GROUP_GAIN3") groupGain3: String?,
                @Field("GROUP_GAIN4") groupGain4: String?,
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
        fun getGroupAllByIds(id: String?, depart: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).getGroupAllByIds(id, depart)
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

        fun getTournamentList(id: String?, depart: String?) : Observable<TournamentRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).getTournamentList(id, depart)
        }
        fun getTournamentAllByIds(id: String?, depart: String?) : Observable<ServerRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).getTournamentAllByIds(id, depart)
        }
        fun getContestList(): Observable<ContestResponse> {
            return RetrofitCreator.create(ApiImpl::class.java).getContestList()
        }
        fun getContestAllByIds(id : String): Observable<ContestResponse> {
            return RetrofitCreator.create(ApiImpl::class.java).getContestAllByIds(id)
        }
        fun postMatchCreator(uid: String?, type: String?, my: String?, partner: String?, other: String?, otherPartner: String?, date: String?, result: String?, win: String?, lose: String?) : Observable<ServerRepo>{
            return RetrofitCreator.create(ApiImpl::class.java).postMatchCreator(uid, type, my, partner, other, otherPartner, date, result, win, lose)
        }

        fun postTournamentCreator(id: String?, uid: String?, type: String?, round: String?, number: Int?, depart: String?, player1: String?, player2: String?, score1: String?, score2: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postTournamentCreator(id, uid, type, round, number, depart, player1, player2, score1, score2)
        }
        fun postUpdateTournament(id: String?, uid: String?, type: String?, round: String?, number: Int?, depart: String?, player1: String?, player2: String?, score1: String?, score2: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postUpdateTournament(id, uid, type, round, number, depart, player1, player2, score1, score2)
        }
        fun postGroupCreator(id: String?, uid: String?, depart: String?, number: Int?, player1: String?, player2: String?, player3: String?, player4: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postGroupCreator(id, uid, depart, number, player1, player2, player3, player4)
        }

        fun postUserCreator(email: String?, nickName: String?, phone: String?, birthday: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postUserCreator(email, nickName, phone, birthday)
        }

        fun postContestCreator(id: String?, name: String?, date: String?, end: String?, type: String?, host: String?, location: String?, depart: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postContestCreator(id, name, date, end, type, host, location, depart)
        }

        fun postApplicationCreator(id: String?, uid: String?, depart: String?, type: Int?, name: String?, phone: String?, partner: String?, partnerPhone: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postApplicationCreator(id, uid, depart, type, name, phone, partner, partnerPhone)
        }

        fun postUpdateGroup(groupID: String?, groupDepart: String?, groupNumber: String?, groupScore1: String?, groupScore2: String?, groupScore3: String?, groupScore4: String?, groupScore5: String?, groupScore6: String?,
                            groupGain1: String?, groupGain2: String?, groupGain3: String?, groupGain4: String?, groupTotal1: String?, groupTotal2: String?, groupTotal3: String?, groupTotal4: String?,
                            groupRank1: String?, groupRank2: String?, groupRank3: String?, groupRank4: String?): Observable<ServerRepo> {
            return RetrofitCreator.create(ApiImpl::class.java).postUpdateGroup(groupID, groupDepart, groupNumber, groupScore1, groupScore2, groupScore3, groupScore4,
                    groupScore5, groupScore6, groupGain1, groupGain2, groupGain3, groupGain4, groupTotal1, groupTotal2, groupTotal3, groupTotal4, groupRank1, groupRank2, groupRank3, groupRank4)
        }
    }
}