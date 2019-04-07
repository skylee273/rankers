package com.project.rankers.ui.form

import androidx.databinding.ObservableField
import com.project.rankers.ui.base.BaseViewModel
import com.project.rankers.data.model.db.USER
import com.project.rankers.data.remote.api.Api
import com.project.rankers.utils.CommonUtils
import io.reactivex.schedulers.Schedulers

class ApplicationFormViewModel : BaseViewModel<ApplicationFormNavigator>() {

    private var user: USER? = null
    val userName = ObservableField<String>()
    val userPhone = ObservableField<String>()
    val partnerName = ObservableField<String>()
    val partnerPhone = ObservableField<String>()
    var contestID: String? = null
    var contestDepart: String? = null
    var contestType: Int? = null
    val userEmail: String
        get() = user!!.geteMail()

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

    fun setContestInfo(contestID: String, contestDepart: String) {
        this.contestID = contestID
        this.contestDepart = contestDepart
    }

    fun onFormClick() {
        if (setType()) {
            createForm()
        }

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
        user = USER()
        compositeDisposable.add(Api.postApplicationCreator(contestID, userEmail, contestDepart, contestType, getUserName(), getUserPhone(), getPartnerName(), getPartnerPhone())
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
}