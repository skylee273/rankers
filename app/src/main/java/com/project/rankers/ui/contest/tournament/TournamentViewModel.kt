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
    var isSuccess = true

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
                            tournamentUserList.add(TournamentUser(item.groupPlayer1!!, item.groupRank1!!, item.groupNumber!!))
                        }
                        if (item.groupRank2!!.toInt() == 1 || item.groupRank2!!.toInt() == 2) {
                            tournamentUserList.add(TournamentUser(item.groupPlayer2!!, item.groupRank2!!, item.groupNumber!!))
                        }
                        if (item.groupRank3!!.toInt() == 1 || item.groupRank3!!.toInt() == 2) {
                            tournamentUserList.add(TournamentUser(item.groupPlayer3!!, item.groupRank3!!, item.groupNumber!!))
                        }
                        if (item.groupRank4!!.toInt() == 1 || item.groupRank4!!.toInt() == 2) {
                            tournamentUserList.add(TournamentUser(item.groupPlayer4!!, item.groupRank4!!, item.groupNumber!!))
                        }
                    }

                    var count = 1
                    val round = nextPowerOf2(tournamentUserList.size)
                    for( i in 0 until  tournamentUserList.size step 2){
                        if(tournamentUserList.size % 2 != 0 && i == (tournamentUserList.size-1)){
                            tournamentList.add(Tournament((count++).toString(), tournamentUserList[i].name, tournamentUserList[i].rank, "-", "-", tournamentUserList[i].number))
                        }else{
                            tournamentList.add(Tournament((count++).toString(), tournamentUserList[i].name, tournamentUserList[i].rank, tournamentUserList[i + 1].name, tournamentUserList[i + 1].rank, tournamentUserList[i].number))
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

    fun uploadTournament(tournamentItem: List<Tournament>) {

        val round = nextPowerOf2(tournamentItem.size * 2)
        val totalGame = logB(round.toDouble(), 2.0) // 5

        var mod = 2
        var index = 1
        for (i in 0 until totalGame.toInt()) {
            for (j in 0 until (round / mod)) {     // 0 ~ 31
                if (mod == 2) {
                    if (j < tournamentItem.size) {
                        uploadTournament(contestID!!, User().userID, "1", round.toString(), index, contestDepartName!!, tournamentItem[j].teamOneName, tournamentItem[j].teamTwoName, "0", "0")
                    } else {
                        uploadTournament(contestID!!, User().userID, "1", round.toString(), index, contestDepartName!!, "-", "-", "0", "0")
                    }
                } else {
                    uploadTournament(contestID!!, User().userID, "1", round.toString(), index, contestDepartName!!, "경기전", "경기전", "0", "0")
                }
                index++
            }
            mod *= 2
        }
        if (isSuccess) {
            navigator.showDialog("토너먼트 등록", "토너먼트 대진표를 완성하였습니다.")
            setIsLoading(false)
        }

    }

    private fun uploadTournament(contestID: String, userId: String, type: String, round: String, number: Int, deaprtName: String, playerOneName: String, playerTwoName: String, scoreOne: String, scoreTwo: String) {
        setIsLoading(true)
        compositeDisposable.add(Api.postTournamentCreator(contestID, userId, type, round, number, deaprtName, playerOneName, playerTwoName, scoreOne, scoreTwo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.getSuccess())
                        isSuccess = false
                }) {
                    isSuccess = false
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    private fun logB(x: Double, base: Double): Double {
        return Math.log(x) / Math.log(base)
    }

    private fun nextPowerOf2(number: Int): Int {
        var count = 0
        var n = number
        if (n > 0 && n and n - 1 == 0)
            return n

        while (n != 0) {
            n = n shr 1
            count += 1
        }

        return 1 shl count
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