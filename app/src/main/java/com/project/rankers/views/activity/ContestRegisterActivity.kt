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

import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input

import com.project.rankers.adapter.DepartAdapter
import com.project.rankers.model.DEPART
import com.project.rankers.model.USER
import com.project.rankers.retrofit.api.Api
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList


class ContestRegisterActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var contestRegisterBinding: ActivityContestRegisterBinding
    private val PICK_PDF_REQUEST = 1
    private var filePath: Uri? = null
    private val viewModel = ContestRegisterViewModel()
    private var arrayDepart = ArrayList<DEPART>()
    private var linearLayoutManager = LinearLayoutManager(this)
    var USER: USER? = null

    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        USER = USER()

        contestRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_contest_register)
        contestRegisterBinding.setVariable(BR.rm, viewModel)
        contestRegisterBinding.setVariable(BR.contest, this)


        contestRegisterBinding.recycler.setHasFixedSize(true)
        contestRegisterBinding.recycler.layoutManager = linearLayoutManager
        contestRegisterBinding.recycler.adapter = DepartAdapter(this, arrayDepart)

        val mToolbar = contestRegisterBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        compositeDisposable = CompositeDisposable()

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
       MaterialDialog(this).show {
            title(R.string.title)
            input (
                    hint = "부서를 입력해주세요"
            ){
                _, text ->
                arrayDepart.add(DEPART("$text"))
            }
            positiveButton { R.string.agree }
            negativeButton(R.string.disagree)
        }

    }

    fun regitClick() {
        var depart = ""
        for (s in arrayDepart) {
            depart += (s.depart + ",")
        }
        createContest(USER!!.geteMail(), contestRegisterBinding.editTitle.text.toString(), contestRegisterBinding.editDate.text.toString(),
                contestRegisterBinding.editEnd.text.toString(), contestRegisterBinding.editType.text.toString(), contestRegisterBinding.editHost.text.toString(),
                contestRegisterBinding.editLocation.text.toString(), depart)
    }

    fun fileClick() {
        showFileChooser()
    }

    private fun createContest(id: String?, name: String?, start: String?, end: String?, type: String?, host: String?, location: String?, depart: String?) {
        compositeDisposable.add(Api.postContestCreator(id, name, start, end, type, host, location, depart)
                .subscribeOn(Schedulers.newThread())
                .take(4)
                .subscribe({
                    if(it.getSuccess()){
                        finish()
                    }
                }) {
                    // 에러블록
                    Log.e("MyTag", "${it.message}")
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


}

