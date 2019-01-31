package com.project.rankers.views

import android.app.Activity
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.databinding.library.baseAdapters.BR
import com.project.rankers.R
import com.project.rankers.databinding.ActivityContestRegisterBinding
import com.project.rankers.viewmodels.ContestRegisterViewModel
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.project.rankers.retrofit.`interface`.RankersUser
import com.project.rankers.retrofit.crater.RankersPostCreator
import com.project.rankers.retrofit.models.RankersServerRepo
import com.project.rankers.util.FilePath
import net.gotev.uploadservice.UploadNotificationConfig
import net.gotev.uploadservice.MultipartUploadRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ContestRegisterActivity : AppCompatActivity() {

    private lateinit var contestRegisterBinding: ActivityContestRegisterBinding
    private val PICK_PDF_REQUEST = 1
    private var filePath : Uri? = null
    lateinit var mContext: Context
    private val viewModel = ContestRegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        contestRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_contest_register)
        contestRegisterBinding.setVariable(BR.rm, viewModel)
        contestRegisterBinding.setVariable(BR.contest, this)

    }


    fun fileClick() {
        showFileChooser()
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

