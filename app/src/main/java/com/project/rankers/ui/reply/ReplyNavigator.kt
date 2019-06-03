package com.project.rankers.ui.reply

import com.project.rankers.data.remote.response.ReplyRepo

interface ReplyNavigator {
    fun handleError(throwable: Throwable)
    fun showReplyList(userItem : List<ReplyRepo.Reply>)
    fun showLengthDialog()
    fun failDialog()
}