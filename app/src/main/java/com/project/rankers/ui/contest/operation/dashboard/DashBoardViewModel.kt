package com.project.rankers.ui.contest.operation.dashboard

import com.project.rankers.data.remote.api.Api
import com.project.rankers.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DashBoardViewModel : BaseViewModel<DashBoardNavigator>() {


    fun isTournament(contestID: String, contestDepartName: String) {
        setIsLoading(true)
        compositeDisposable.add(Api.getTournamentAllByIds(contestID, contestDepartName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.getSuccess()) {
                        navigator.showDialog("토너먼트 대진표", "생성된 대진표가 존재합니다.")
                    } else {
                        navigator.openTournamentActivity()
                    }
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }

    fun isLeague(contestID: String, contestDepartName: String) {
        setIsLoading(true)
        compositeDisposable.add(Api.getGroupAllByIds(contestID, contestDepartName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.getSuccess() ) {
                        navigator.showDialog("예선전 대진표", "생성된 대진표가 존재합니다.")
                    } else {
                        navigator.openLeagueActivity()
                    }
                    setIsLoading(false)
                }) {
                    setIsLoading(false)
                    navigator.handleError(it)
                })
    }
}