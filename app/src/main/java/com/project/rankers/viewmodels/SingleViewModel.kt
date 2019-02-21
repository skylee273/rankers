package com.project.rankers.viewmodels

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.project.rankers.R
import com.project.rankers.dialog.LocationDialog
import java.util.*

class SingleViewModel  : BaseObservable() {

    val dateTime = ObservableField("")
    val result = ObservableField("")
    val myItems = listOf(" 승 ", " 패 ")
    var sResult : String = ""

    private val cal : Calendar? = Calendar.getInstance()


    fun dateClick(view : View?){
        dateRequest(view!!.context)
    }

    fun resultClick(view : View?){
        resultRequest(view!!.context)
    }

    private fun resultRequest(context: Context){
        context.setTheme(R.style.DialogTeme)
        MaterialDialog(context).show {
            title(R.string.result_title)
            listItemsSingleChoice(items = myItems) { _, _, text ->
                sResult = text
            }
            onDismiss {
                result.set(sResult)
            }
            positiveButton ( R.string.agree )
        }
    }

    fun dateRequest(context: Context){
        val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, month, date ->
            val time = String.format("%04d.%02d.%02d", year, month + 1, date)
            dateTime.set(time)
        }, cal!!.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
        dialog.datePicker.maxDate = Date().time    //입력한 날짜 이후로 클릭 안되게 옵션f
        dialog.show()
    }



}