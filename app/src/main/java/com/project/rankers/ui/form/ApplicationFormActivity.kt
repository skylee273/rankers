package com.project.rankers.ui.form

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.databinding.ActivityApplicationFormBinding
import java.util.*
import javax.inject.Inject

class ApplicationFormActivity : BaseActivity<ActivityApplicationFormBinding, ApplicationFormViewModel>(), ApplicationFormNavigator {

    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private lateinit var applicationFormBinding: ActivityApplicationFormBinding
    private var mApplicationFormViewModel : ApplicationFormViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_application_form
    }
    override fun getBindingVariable(): Int {
       return BR.viewModel
    }
    override fun getViewModel(): ApplicationFormViewModel {
        mApplicationFormViewModel = ViewModelProviders.of(this, factory).get(ApplicationFormViewModel::class.java)
        return mApplicationFormViewModel as ApplicationFormViewModel
    }
    override fun showDialog(title: String, message: String) {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = title)
                message(text =  message)
                positiveButton(text = "확인")
            }
        }
    }
    override fun handleError(throwable: Throwable) {
        Log.e("form",throwable.message)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationFormBinding = getViewDataBinding()
        mApplicationFormViewModel!!.navigator = this


        val mToolbar = applicationFormBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        setContestInfo()

    }
    private fun setContestInfo(){
        val intent = intent
        mApplicationFormViewModel!!.setContestInfo(intent.extras.getString("id"),intent.extras.getString("departName"))
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
}