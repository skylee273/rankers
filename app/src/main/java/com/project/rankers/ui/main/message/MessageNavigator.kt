package com.project.rankers.ui.main.message

import com.project.rankers.data.remote.response.BoardRepo

interface MessageNavigator {
    fun handleError(throwable: Throwable)
    fun updateBoard(boardItem: List<BoardRepo.Board>)
    fun openBoardActivity()
}