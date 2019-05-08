package com.project.rankers.ui.contest.tournament

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.model.db.Tournament
import com.project.rankers.data.model.db.TournamentUser
import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TournamentViewModel : BaseViewModel<TournamentNavigator>() {

    var mutableLiveData: MutableLiveData<List<GroupResponse.Group>>
    var contestID: String? = null
    var contestDepartName: String? = null
    var departName: String? = null
    var tournamentUserList: ArrayList<TournamentUser>
    var tournamentList: ArrayList<Tournament>

    init {
        mutableLiveData = MutableLiveData()
        tournamentUserList = ArrayList()
        tournamentList = ArrayList()
    }

    fun fetchTournament() {
        setIsLoading(true)
        compositeDisposable.add(Api.getGroupList(contestID, contestDepartName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: GroupResponse ->
                    for (item in response.items) {
                        departName = item.groupDepart
                        if (item.groupRank1!!.toInt() == 1 || item.groupRank1!!.toInt() == 2) {
                            tournamentUserList.add(TournamentUser(item.groupPlayer1!!, item.groupRank1!!))
                        }
                        if (item.groupRank2!!.toInt() == 1 || item.groupRank2!!.toInt() == 2) {
                            tournamentUserList.add(TournamentUser(item.groupPlayer2!!, item.groupRank2!!))
                        }
                        if (item.groupRank3!!.toInt() == 1 || item.groupRank3!!.toInt() == 2) {
                            tournamentUserList.add(TournamentUser(item.groupPlayer3!!, item.groupRank3!!))
                        }
                        if (item.groupRank4!!.toInt() == 1 || item.groupRank4!!.toInt() == 2) {
                            tournamentUserList.add(TournamentUser(item.groupPlayer4!!, item.groupRank4!!))
                        }
                    }

                    for (i in 0 until tournamentUserList.size step 2) {
                        if((tournamentList.size % 2) != 0 && (tournamentUserList.size-1) == i){
                            tournamentList.add(Tournament(departName!!, tournamentUserList[i].name, tournamentUserList[i].rank, "", ""))
                            break
                        }
                        else{
                            tournamentList.add(Tournament(departName!!, tournamentUserList[i].name, tournamentUserList[i].rank, tournamentUserList[i + 1].name, tournamentUserList[i + 1].rank))
                        }
                    }
                    navigator.updateTournament(tournamentList)
                    mutableLiveData.postValue(response.items)
                    mutableLiveData.value = response.items
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    fun uploadTournament(tournamentItem: List<Tournament>){
        setIsLoading(true)
        var isSuccess  = true
        for(item in tournamentItem){
            compositeDisposable.add(Api.postTournamentCreator(contestID, User().userID, "0", contestDepartName,item.teamOneName, item.teamTwoName, "0", "0")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(!it.getSuccess())
                            isSuccess = false
                    }) {
                        setIsLoading(false)
                        navigator.handleError(it)
                    })
        }
        if(isSuccess){
            navigator.showDialog("토너먼트 등록", "토너먼드 대진표를 완성하였습니다.")
            setIsLoading(false)
        }

    }

    fun onUploadClick() {
        navigator.uploadTournament()
    }


    fun setContestInfo(contestID: String, contestDepartName: String) {
        this.contestID = contestID
        this.contestDepartName = contestDepartName
        fetchTournament()
    }

    fun getListLiveData(): MutableLiveData<List<GroupResponse.Group>> {
        return mutableLiveData
    }

}