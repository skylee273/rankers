package com.project.rankers.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.project.rankers.dialog.LocationDialog
import android.app.DatePickerDialog
import java.util.*
import kotlin.collections.HashMap


class ContestRegisterViewModel : BaseObservable() {


    val location = ObservableField("")
    val type = ObservableField("")
    val name = ObservableField("")
    val dateTime = ObservableField("")
    val file = ObservableField("")
    val cal : Calendar? = Calendar.getInstance()
    val mutableMap : HashMap<String ,String?> = hashMapOf()
    
    private var locationDialog: LocationDialog? = null

    init {
        location.set(initLocation())
        type.set(initType())
        name.set(initName())
        dateTime.set(initDate())
        file.set(initFile())
    }

    private fun initLocation() : String = "장소"
    private fun initType() : String  = "대회종류"
    private fun initName() : String  = "대회명"
    private fun initDate() : String  = "날짜"
    private fun initFile() : String  = "첨부파일"

    // databinding 을 통해 view 와 연결
    fun locationClick(view : View?){
        locationRequest(view!!.context)
    }

    fun dateClick(view : View?){
        dateRequest(view!!.context)
    }

    fun dateRequest(context: Context){
        val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
            val time = String.format("%4d.%2d.%2d", year, month + 1, date)
            dateTime.set(time)
        }, cal!!.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
        dialog.datePicker.maxDate = Date().getTime()    //입력한 날짜 이후로 클릭 안되게 옵션
        dialog.show()
    }

    fun locationRequest(context: Context){
        locationDialog = LocationDialog(context)
        locationDialog!!.setCancelable(true)
        locationDialog!!.setTitle("주소검색")
        locationDialog!!.show()
        locationDialog!!.setOnDismissListener({ location.set(locationDialog!!.getAddressStr()) })
    }
    fun checkValue () : Boolean?{
        var checkFlag : Boolean? = true
        mutableMap["Location"] = location.get()
        mutableMap["Type"] = type.get()
        mutableMap["Nmae"] = name.get()
        mutableMap["Date"] = dateTime.get()
        mutableMap["FILE"] = file.get()

        for((key, value) in mutableMap){
            if(value.equals("")){
                checkFlag = false
            }
        }
        return checkFlag
    }

}