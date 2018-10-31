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
import android.util.Log
import android.widget.Toast
import com.project.rankers.util.FilePath
import net.gotev.uploadservice.UploadNotificationConfig
import net.gotev.uploadservice.MultipartUploadRequest
import java.util.*


class ContestRegisterActivity : AppCompatActivity() {

    val UPLOAD_URL = "http://internetfaqs.net/AndroidPdfUpload/upload.php"
    val PDF_FETCH_URL = "http://internetfaqs.net/AndroidPdfUpload/getPdfs.php"
    private lateinit var contestRegisterBinding: ActivityContestRegisterBinding
    private val PICK_PDF_REQUEST = 1
    private var filePath : Uri? = null

    lateinit var mContext: Context
    val viewModel = ContestRegisterViewModel()

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

    /*
    * This is the method responsible for pdf upload
    * We need the full pdf path and the name for the pdf in this method
    * */

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun uploadMultipart() {
         val name = "test"

        //getting the actual path of the image

        val path = FilePath.getPath(this, filePath)

        if (path == null) {

            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show()
        } else {
            //Uploading code
            try {
                val uploadId = UUID.randomUUID().toString()

                //Creating a multi part request
                MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                        .addFileToUpload(path, "pdf") //Adding file
                        .addParameter("name", name) //Adding text parameter to the request
                        .setNotificationConfig(UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload() //Starting the upload

            } catch (exc: Exception) {
                Toast.makeText(this, exc.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
}

