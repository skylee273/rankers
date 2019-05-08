package com.project.rankers.ui.contest.form

import com.project.rankers.data.remote.response.UserRepo

interface ApplicationFormNavigator {
    fun showDialog(title : String, message : String)
    fun showUserList(matchItem : List<UserRepo.User>)
    fun handleError(throwable: Throwable)
}