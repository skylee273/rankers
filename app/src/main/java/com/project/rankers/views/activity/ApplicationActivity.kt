package com.project.rankers.views.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.databinding.ActivityApplicationBinding
import com.project.rankers.model.USER
import com.project.rankers.retrofit.api.Api
import com.project.rankers.viewmodels.ApplicationViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ApplicationActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var compositeDisposable: CompositeDisposable
    private lateinit var applicationBinding: ActivityApplicationBinding
    private val viewModel = ApplicationViewModel()

    var id: String? = null
    var dpeart : String? = null
    val email: String
        get() = user!!.geteMail()
    val name: String
        get() = applicationBinding.editName.text.toString()
    val phone: String
        get() = applicationBinding.editPhone.text.toString()
    val partnerName: String
        get() = applicationBinding.editPartnerName.text.toString()
    val partnerPhone: String
        get() = applicationBinding.editPartnerPhone.text.toString()

    var user: USER? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        user = USER()
        applicationBinding = DataBindingUtil.setContentView(this, R.layout.activity_application)
        applicationBinding.setVariable(BR.viewModel, viewModel)
        applicationBinding.setVariable(BR.activity, this)

        val mToolbar = applicationBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val intent = intent
        id = intent.extras.getString("id")
        dpeart = intent.extras.getString("depart")
        compositeDisposable = CompositeDisposable()

    }

    fun regitClick() {
        if (!isMulti()) {
            if (checkSingle()) {
                uploadApllcation(0, "x", "x")
            } else {
                reTry()
            }
        } else {
            if (checkMulit()) {
                uploadApllcation(1, partnerName, partnerPhone)
            } else {
                reTry()
            }
        }
    }

    private fun uploadApllcation(type: Int, str: String, str1: String) {
        compositeDisposable.add(Api.postApplicationCreator(id, email, dpeart, type, name, phone, str, str1)
                .subscribeOn(Schedulers.newThread())
                .take(4)
                .subscribe({
                    if (it.getSuccess()) {
                        (this).runOnUiThread {
                            successDialog()
                        }
                    }
                }) {
                    (this).runOnUiThread {
                        failDialog()
                    }
                    Log.e("MyTag", "${it.message}")
                })
    }

    private fun isMulti(): Boolean {
        return partnerName.isNotEmpty() && partnerPhone.isNotEmpty()
    }

    private fun checkMulit(): Boolean {
        return partnerName.isNotEmpty() && partnerPhone.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()
    }

    private fun checkSingle(): Boolean {
        return name.isNotEmpty() && phone.isNotEmpty()
    }

    private fun failDialog() {
        MaterialDialog(this).show {
            title(text = "대회신청")
            message(text = "대회 신청이 실패하였습니다.")
            positiveButton(text = "확인")
        }
    }

    private fun successDialog() {
        MaterialDialog(this).show {
            title(text = "대회신청")
            message(text = "대회 신청이 완료 되었습니다.")
            positiveButton(text = "확인") {
                finish()
            }
        }
    }

    private fun reTry() {
        MaterialDialog(this).show {
            title(text = "대회신청")
            message(text = "정보를 정확히 입력해주세요")
            positiveButton(text = "확인")
        }
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
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}