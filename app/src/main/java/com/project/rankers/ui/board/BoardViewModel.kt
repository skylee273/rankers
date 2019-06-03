package com.project.rankers.ui.board

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.UserRepo
import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.utils.CommonUtils
import io.reactivex.schedulers.Schedulers

class BoardViewModel : BaseViewModel<BoardNavigator>() {

    var mutableLiveData : MutableLiveData<List<UserRepo.User>>
    var title = ObservableField<String>()
    var text = ObservableField<String>()

    init {
        mutableLiveData = MutableLiveData()
    }
    fun getTitle(): String? {
        return title.get()
    }

    fun getText(): String? {
        return text.get()
    }

    fun onFormClick() {
        if (setType()) {
            createForm()
        }
    }


    private fun setType(): Boolean {
        if (!CommonUtils.isEmpty(getTitle()) && !CommonUtils.isEmpty(getText())) {
            return true
        }  else {
            navigator.showDialog("신청서 작성", "정보를 정확히 입력해주세요")
        }
        return false

    }

    private fun createForm() {
        compositeDisposable.add(Api.postBoardCreator(User().userID, User().userName,CommonUtils.date,getTitle(),getText())
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if (it.getSuccess()) {
                        navigator.successDialog("게시판 등록", "게시판 등록이 완료되었습니다")
                    }
                }) {
                    navigator.showDialog("게시판 등록", "게시판 등록에 실패하였습니다")
                    navigator.handleError(it)
                })
    }
    fun getListLiveData(): MutableLiveData<List<UserRepo.User>> {
        return mutableLiveData
    }

}