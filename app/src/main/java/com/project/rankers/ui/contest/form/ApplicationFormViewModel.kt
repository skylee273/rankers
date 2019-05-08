package com.project.rankers.ui.contest.form

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.project.rankers.data.model.db.User
import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.MatchRepo
import com.project.rankers.data.remote.response.UserRepo
import com.project.rankers.utils.CommonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApplicationFormViewModel : BaseViewModel<ApplicationFormNavigator>() {

    var mutableLiveData : MutableLiveData<List<UserRepo.User>>
    var userName = ObservableField<String>()
    var userPhone = ObservableField<String>()
    var partnerName = ObservableField<String>()
    var partnerPhone = ObservableField<String>()
    var contestID: String? = null
    var contestDepart: String? = null
    var contestType: Int? = null
    val user = User()
    init {
        mutableLiveData = MutableLiveData()
        fetchUsers()
        userName = ObservableField(user.userName)
        userPhone = ObservableField(user.userPhone)
    }
    fun getUserName(): String? {
        return userName.get()
    }

    fun getUserPhone(): String? {
        return userPhone.get()
    }

    fun getPartnerName(): String? {
        return partnerName.get()
    }

    fun getPartnerPhone(): String? {
        return partnerPhone.get()
    }

    fun setPartner(name : String, phone : String){
        partnerName.set(name)
        partnerPhone.set(phone)
    }
    fun setContestInfo(contestID: String, contestDepart: String) {
        this.contestID = contestID
        this.contestDepart = contestDepart
    }

    fun onFormClick() {
        if (setType()) {
            createForm()
        }
    }

    private fun fetchUsers() {
        compositeDisposable.add(Api.getUserAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    navigator.showUserList(response.items)
                }, { error: Throwable ->
                    navigator.handleError(error)
                })
        )
    }

    private fun setType(): Boolean {

        if (!CommonUtils.isEmpty(getUserName()) && !CommonUtils.isEmpty(getUserPhone()) && !CommonUtils.isEmpty(getPartnerName()) && !CommonUtils.isEmpty(getPartnerPhone()) ) {
            contestType = 1
            return true
        } else if (!CommonUtils.isEmpty(getUserName()) && !CommonUtils.isEmpty(getUserPhone()) && CommonUtils.isEmpty(getPartnerName()) && CommonUtils.isEmpty(getPartnerPhone())) {
            partnerName.set("x")
            partnerPhone.set("x")
            contestType = 0
            return true
        } else {
            navigator.showDialog("신청서 작성", "정보를 정확히 입력해주세요")
        }
        return false

    }

    private fun createForm() {
        compositeDisposable.add(Api.postApplicationCreator(contestID, User().userID, contestDepart, contestType, getUserName(), getUserPhone(), getPartnerName(), getPartnerPhone())
                .subscribeOn(Schedulers.newThread())
                .take(4)
                .subscribe({
                    if (it.getSuccess()) {
                        navigator.showDialog("신청서작성", "신청서 작성이 완료되었습니다")
                    }
                }) {
                    navigator.showDialog("신청서작성", "신청서 작성에 실패하였습니다")
                    navigator.handleError(it)
                })
    }
    fun getListLiveData(): MutableLiveData<List<UserRepo.User>> {
        return mutableLiveData
    }

}