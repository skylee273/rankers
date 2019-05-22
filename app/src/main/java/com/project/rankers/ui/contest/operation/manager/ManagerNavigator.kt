package com.project.rankers.ui.contest.operation.manager

import com.project.rankers.data.remote.response.UserRepo

interface ManagerNavigator {
    fun handleError(throwable: Throwable)
    fun showUserList(userItem : List<UserRepo.User>)
    fun failDialog()
}