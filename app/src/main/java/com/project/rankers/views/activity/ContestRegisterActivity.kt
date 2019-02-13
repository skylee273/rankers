package com.project.rankers.views.activity

import android.app.Activity
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.rankers.R
import com.project.rankers.databinding.ActivityContestRegisterBinding
import com.project.rankers.viewmodels.ContestRegisterViewModel
import android.content.Intent
import android.net.Uri
import android.text.InputType

import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.EditText
import androidx.databinding.library.baseAdapters.BR
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItemsSingleChoice

import com.project.rankers.adprer.DepartAdapter
import com.project.rankers.model.DEPART
import com.project.rankers.model.User
import com.project.rankers.retrofit.`interface`.Contest
import com.project.rankers.retrofit.crater.RankersPostCreator
import com.project.rankers.retrofit.models.RankersServerRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ContestRegisterActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var contestRegisterBinding: ActivityContestRegisterBinding
    private val PICK_PDF_REQUEST = 1
    private var filePath: Uri? = null
    private val viewModel = ContestRegisterViewModel()
    private var Depart = ArrayList<DEPART>()
    private var LinearLayoutManager = LinearLayoutManager(this)
    var rankersServerRepo: RankersServerRepo? = null
    var user: User? = null
    val myItems = listOf(" 단식 ", " 복식 ")
    var sType : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        user = User()

        contestRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_contest_register)
        contestRegisterBinding.setVariable(BR.rm, viewModel)
        contestRegisterBinding.setVariable(BR.contest, this)


        contestRegisterBinding.recycler.setHasFixedSize(true)
        contestRegisterBinding.recycler.layoutManager = LinearLayoutManager
        contestRegisterBinding.recycler.adapter = DepartAdapter(this, Depart)

        val mToolbar = contestRegisterBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun addDepart() {
        this.setTheme(R.style.DialogTeme)
        val dialog = MaterialDialog(this).show {
            title(R.string.title)
            customView(R.layout.dialog_depart, scrollable = true)
            positiveButton (R.string.agree){ dialog ->
                val departInput : EditText = dialog.getCustomView().findViewById(R.id.edit_depart)
                //Depart.add(DEPART(departInput.text.toString()))
            }
            negativeButton(R.string.disagree)
        }
        //Setup Custom View Content
        val customView = dialog.getCustomView()
        val departInput : EditText = customView.findViewById(R.id.edit_depart)
        val singleCheck : CheckBox = customView.findViewById(R.id.check_single)
        val multiCheck : CheckBox = customView.findViewById(R.id.check_multi)
        val manCheck : CheckBox = customView.findViewById(R.id.check_man)
        val womenCheck : CheckBox = customView.findViewById(R.id.check_woman)
        singleCheck.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) { }
            if (!isChecked) { }
        }
        multiCheck.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) { }
            if (!isChecked) { }
        }
        manCheck.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) { }
            if (!isChecked) { }
        }
        womenCheck.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) { }
            if (!isChecked) { }
        }

    }

    fun regitClick() {
        var depart = ""
        for (s in Depart) {
            depart += (s.depart + "/")
        }
        createContest(user!!.geteMail(), contestRegisterBinding.editTitle.text.toString(), contestRegisterBinding.editDate.text.toString(),
                contestRegisterBinding.editEnd.text.toString(), contestRegisterBinding.editType.text.toString(), contestRegisterBinding.editHost.text.toString(),
                contestRegisterBinding.editLocation.text.toString(), depart)
    }

    fun fileClick() {
        showFileChooser()
    }

    private fun createContest(id: String?, name: String?, start: String?, end: String?, type: String?, host: String?, location: String?, depart: String?) {
        val server = RankersPostCreator.create(Contest::class.java)
        server.postContestCreator(id, name, start, end, type, host, location, depart).enqueue(object : Callback<RankersServerRepo> {
            override fun onFailure(call: Call<RankersServerRepo>, t: Throwable) {
                Log.d("CONTEST", t.message.toString())
            }

            override fun onResponse(call: Call<RankersServerRepo>, response: Response<RankersServerRepo>) {
                rankersServerRepo = response.body()
                if (rankersServerRepo != null) {
                    if (rankersServerRepo!!.getSuccess()) {
                        mContext.setTheme(R.style.DialogTeme)
                        MaterialDialog(mContext).show {
                            title(R.string.success_title)
                            message(R.string.success_context)
                            positiveButton(R.string.agree) {
                                finish()
                            }
                        }
                    } else {

                    }
                }
            }
        })
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST)
    }

    //handling the image chooser activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            Log.d("File Path", filePath.toString())
        }
    }

}

