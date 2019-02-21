package com.project.rankers.viewmodels

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import android.view.View
import com.project.rankers.dialog.LocationDialog
import android.app.DatePickerDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.project.rankers.R
import java.util.*
import kotlin.collections.HashMap


class ContestRegisterViewModel : BaseObservable() {

    private val TAG = "DataViewModel"

    val location = ObservableField("")
    val type = ObservableField("")
    val name = ObservableField("")
    val dateTime = ObservableField("")
    val endTime = ObservableField("")
    val file = ObservableField("")
    val host = ObservableField("")
    private val cal : Calendar? = Calendar.getInstance()
    private val mutableMap : HashMap<String ,String?> = hashMapOf()
    private val myItems = listOf(" LOCAL ", " KTA ", " ETC ")
    var sType : String = ""
    private var locationDialog: LocationDialog? = null

    fun locationClick(view : View?){
        locationRequest(view!!.context)
    }

    fun dateClick(view : View?){
        dateRequest(view!!.context)
    }

    fun endClick(view : View?){
        endRequest(view!!.context)
    }

    fun typeClick(view : View?){
        typeRequest(view!!.context)
    }

    private fun typeRequest(context: Context){
        context.setTheme(R.style.DialogTeme)
        MaterialDialog(context).show {
            title(R.string.type_title)
            listItemsSingleChoice(items = myItems) { _, _, text ->
                sType = text
            }
            onDismiss {
                type.set(sType)
            }
            positiveButton ( R.string.agree )
        }
    }

    private fun endRequest(context: Context){
        val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, month, date ->
            val time = String.format("%04d.%02d.%02d", year, month + 1, date)
            endTime.set(time)
        }, cal!!.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
        dialog.show()
    }

    private fun dateRequest(context: Context){
        val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, month, date ->
            val time = String.format("%4d.%2d.%2d", year, month + 1, date)
            dateTime.set(time)
        }, cal!!.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
        dialog.datePicker.maxDate = Date().time    //입력한 날짜 이후로 클릭 안되게 옵션f
        dialog.show()
    }

    private fun locationRequest(context: Context){
        locationDialog = LocationDialog(context)
        locationDialog!!.setCancelable(true)
        locationDialog!!.setTitle("주소검색")
        locationDialog!!.show()
        locationDialog!!.setOnDismissListener { location.set(locationDialog!!.addressStr) }
    }



    fun checkValue (location : String, type : String, name : String, date : String, host : String) : Boolean?{
        var checkFlag : Boolean? = true
        mutableMap["Location"] = location
        mutableMap["Type"] = type
        mutableMap["Name"] = name
        mutableMap["Date"] = date
        mutableMap["Host"] = host

        for((_, value) in mutableMap){
            if(value.equals("")){
                checkFlag = false
            }
        }
        return checkFlag
    }

}