package com.project.rankers.ui.register

import androidx.databinding.ObservableField
import com.project.rankers.base.BaseViewModel
import com.project.rankers.model.DepartItem
import com.project.rankers.model.USER
import com.project.rankers.retrofit.api.Api
import com.project.rankers.utils.CommonUtils
import io.reactivex.schedulers.Schedulers

class ContestRegisterViewModel : BaseViewModel<ContestRegisterNavigator>() {

    var endDate = ObservableField<String>()
    var file = ObservableField<String>()
    var location = ObservableField<String>()
    var host = ObservableField<String>()
    var type = ObservableField<String>()
    var date = ObservableField<String>()
    var title = ObservableField<String>()
    var user: USER? = null
    private val myItems = listOf(" LOCAL ", " KTA ", " ETC ")
    var arrayDepart = ArrayList<DepartItem>()


    fun getEndDate(): String? {
        return endDate.get()
    }
    fun getDepart() : ArrayList<DepartItem>{
        return arrayDepart
    }
    fun getFile(): String? {
        return file.get()
    }

    fun getLocation(): String? {
        return location.get()
    }

    fun getHost(): String? {
        return host.get()
    }

    fun getType(): String? {
        return type.get()
    }

    fun getDate(): String? {
        return date.get()
    }

    fun getTitle(): String? {
        return title.get()
    }

    // 조금 마음에 안드는 코드들 많음
    fun onRegisterClick() {
        setIsLoading(true)
        user = USER()
        var depart = ""
        for ((index, s) in arrayDepart.withIndex()) {
            depart += if (index > 0)
                ("," + s.depart)
            else
                (s.depart)
        }
        if (isEmptyText()) {
            compositeDisposable.add(Api.postContestCreator(user!!.geteMail(), getTitle(), getDate(), getEndDate(), getType(), getHost(), getLocation(), depart)
                    .subscribeOn(Schedulers.newThread())
                    .take(4)
                    .subscribe({
                        navigator.showDialog("등록성공", "대회등록이 완료되었습니다")
                        setIsLoading(false)
                    }) {
                        navigator.handleError(it)
                        setIsLoading(false)
                    })
        } else {
            navigator.showDialog("등록실패","입력정보를 다시 확인하세요")
        }
    }

    fun onAddClick() {
        navigator.showDepartDialog("부서입력")
    }

    fun onFileClick() {
        navigator.showFileDialog()
    }

    fun onLocationClick() {
        navigator.showLocationDialog("주소검색")
    }

    fun onTypeClick() {
        navigator.showTypeDialog(myItems)
    }

    fun onDateClick() {
        navigator.showDateDialog(0)
    }

    fun onEndDateClick() {
        navigator.showDateDialog(1)
    }

    private fun isEmptyText(): Boolean {
        CommonUtils.isEmpty(user!!.geteMail())
        return CommonUtils.isEmpty(user!!.geteMail()) && CommonUtils.isEmpty(getTitle()) && CommonUtils.isEmpty(getType()) && CommonUtils.isEmpty(getDate()) && CommonUtils.isEmpty(getEndDate()) && CommonUtils.isEmpty(getFile()) && CommonUtils.isEmpty(getHost())
                && CommonUtils.isEmpty(getLocation())
    }

}