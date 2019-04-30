package com.project.rankers.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.ActivityRegisterBinding
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.ui.login.LoginViewModel
import com.project.rankers.ui.main.MainActivity
import javax.inject.Inject

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(), RegisterNavigator {


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mRegisterViewModel : RegisterViewModel? = null
    private lateinit var registerBinding: ActivityRegisterBinding
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): RegisterViewModel {
        mRegisterViewModel = ViewModelProviders.of(this, factory).get(RegisterViewModel::class.java)
        return mRegisterViewModel as RegisterViewModel
    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun handleError(throwable: Throwable) {
        displayLog("Login", throwable.message!!)
    }

    override fun showRetryDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding =  getViewDataBinding()
        mRegisterViewModel!!.navigator = this

        val intent = intent
        val email = intent.extras.getString("userEmail")!!
        mRegisterViewModel!!.setUserEmail(email)
    }
}