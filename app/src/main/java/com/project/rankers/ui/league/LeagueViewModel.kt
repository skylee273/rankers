package com.project.rankers.ui.league

import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.model.db.LeagueItem
import com.project.rankers.data.model.db.USER
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.ApplyRepo
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LeagueViewModel : BaseViewModel<LeagueNavigator>() {

    var mutableLiveData: MutableLiveData<List<ApplyRepo.Apply>>
    var contestID: String? = null
    var contestDepartName: String? = null
    private var peopleItems = mutableListOf<String>()
    private var groupCount : Int = 0
    private  var leagueArray : ArrayList<LeagueItem>? = null
    private var user : USER? =null
    init {
        user = USER()
        mutableLiveData = MutableLiveData()
    }

    private fun fetchLeagues() {
        setIsLoading(true)
        compositeDisposable.add(Api.getApplyList(contestID, contestDepartName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: ApplyRepo ->
                    for (people in response.items) {
                        when (people.applyType!!.toInt()) {
                            0 -> peopleItems.add(people.applyName!!)
                            1 -> peopleItems.add(people.applyName + "," + people.applyPartner)
                        }
                    }
                    groupCount = (response.totalCount / 4) + 1
                    navigator.setGroupCount(response.totalCount, groupCount, peopleItems)
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    fun registerClick() {
        navigator.uploadLeague()
    }
    fun uploadGroup(items : MutableList<LeagueItem>?){
        for ( item in items!!) {
            setIsLoading(true)
            compositeDisposable.add(Api.postGroupCreator(contestID, user!!.geteMail(), contestDepartName, item.groupNumber , item.player1, item.player2, item.player3, item.player4)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.getSuccess()) {
                            navigator.isNextActivity()
                            setIsLoading(false)
                        }else{
                            navigator.failDialog()
                        }
                    }) {
                        setIsLoading(false)
                        navigator.handleError(it)
                    })
        }

    }

    fun plusCountClick(){
        navigator.showGroupNumber(++groupCount)
    }

    fun subCountClick(){
        navigator.showGroupNumber(--groupCount)
    }

    fun createOnClick(){
        leagueArray = ArrayList<LeagueItem>()
        for (number in 1 .. groupCount ) {
            leagueArray!!.add(LeagueItem(number, "", "", "", ""))
        }
        navigator.showRecyclerView(leagueArray!!)
    }
    fun setContestInfo(contestID: String, contestDepartName: String) {
        this.contestID = contestID
        this.contestDepartName = contestDepartName
        fetchLeagues()
    }

}