package com.project.rankers.ui.contest.contestRegister

import android.util.Log
import androidx.databinding.ObservableField
import com.project.rankers.data.model.db.DepartItem
import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.ui.base.BaseViewModel
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
    private val myItems = listOf(" LOCAL ", " KTA ", " ETC ")
    var arrayDepart = ArrayList<DepartItem>()

    fun getEndDate(): String? {
        return endDate.get()
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
        var depart = ""
        for ((index, s) in arrayDepart.withIndex()) {
            depart += if (index > 0)
                ("," + s.depart)
            else
                (s.depart)
        }

        if(isEmptyText(depart)){
            fetchContest(depart)
        }else{
            navigator.showFaildDialog()
        }


    }

    private fun fetchContest(depart : String){
        compositeDisposable.add(Api.postContestCreator(User().userID, getTitle(), getDate(), getEndDate(), getType(), getHost(), getLocation(), depart)
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if (it.getSuccess())
                        navigator.showDialog("등록성공", "대회등록이 완료되었습니다")
                    setIsLoading(false)
                }) {
                    navigator.handleError(it)
                    setIsLoading(false)
                })
    }
    fun onAddClick() {
        navigator.showDepartDialog("부서입력")
    }

    fun onFileClick() {
        navigator.showFileDialog()
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

    private fun isEmptyText(depart : String): Boolean {

        Log.d("EmptyTest", "ID = " + User().userID + " Title = " + getTitle() + " Type = " + getType() + " Date = " + getDate() + " EndDate = " + getEndDate() + " Host = " + getHost() + " Location = " + getLocation())
        return (!CommonUtils.isEmpty(User().userID) && !CommonUtils.isEmpty(getTitle()) && !CommonUtils.isEmpty(getType())
                && !CommonUtils.isEmpty(getDate()) && !CommonUtils.isEmpty(getEndDate()) && !CommonUtils.isEmpty(getHost()) && !CommonUtils.isEmpty(getLocation()) && !CommonUtils.isEmpty(depart))
    }

}