package com.project.rankers.ui.main.rank

import com.project.rankers.data.remote.response.RankRepo

interface RankNavigator {
    fun handleError(throwable: Throwable)
    fun showRankList(rankItem : List<RankRepo.Rank>)
    fun failDialog()
}