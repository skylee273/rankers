package com.project.rankers.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.project.rankers.dialog.LocationDialog
import android.app.DatePickerDialog
import android.databinding.Bindable
import android.util.Log
import com.project.rankers.BR
import com.project.rankers.retrofit.`interface`.Contest
import com.project.rankers.retrofit.`interface`.RankersUser
import com.project.rankers.retrofit.crater.RankersPostCreator
import com.project.rankers.retrofit.models.RankersServerRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap


class ContestRegisterViewModel : BaseObservable() {

    private val TAG = "DataViewModel"

    val location = ObservableField("")
    val type = ObservableField("")
    val name = ObservableField("")
    val dateTime = ObservableField("")
    val file = ObservableField("")
    val host = ObservableField("")
    val depart = ObservableField("")
    val cal : Calendar? = Calendar.getInstance()
    val mutableMap : HashMap<String ,String?> = hashMapOf()
    
    private var locationDialog: LocationDialog? = null

    // databinding 을 통해 view 와 연결
    fun locationClick(view : View?){
        locationRequest(view!!.context)
    }

    fun dateClick(view : View?){
        dateRequest(view!!.context)
    }

    fun dateRequest(context: Context){
        val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, month, date ->
            val time = String.format("%4d.%2d.%2d", year, month + 1, date)
            dateTime.set(time)
        }, cal!!.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
        dialog.datePicker.maxDate = Date().time    //입력한 날짜 이후로 클릭 안되게 옵션f
        dialog.show()
    }

    fun locationRequest(context: Context){
        locationDialog = LocationDialog(context)
        locationDialog!!.setCancelable(true)
        locationDialog!!.setTitle("주소검색")
        locationDialog!!.show()
        locationDialog!!.setOnDismissListener { location.set(locationDialog!!.addressStr) }
    }

    fun checkValue (location : String, type : String, name : String, date : String, host : String, depart : String) : Boolean?{
        var checkFlag : Boolean? = true
        mutableMap["Location"] = location
        mutableMap["Type"] = type
        mutableMap["Name"] = name
        mutableMap["Date"] = date
        mutableMap["Host"] = host
        mutableMap["Depart"] = depart

        for((_, value) in mutableMap){
            if(value.equals("")){
                checkFlag = false
            }
        }
        return checkFlag
    }

}