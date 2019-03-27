package com.project.rankers.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.LayoutRes
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.project.rankers.R
import com.project.rankers.model.USER
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>>:  AppCompatActivity(){

    private val mProgressDialog: Process? = null
    lateinit var mViewDataBinding: T
    private var mViewModel: V? = null
    lateinit var mContext: Context
    var user: USER? = null
    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getBindingVariable(): Int
    abstract fun getViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        setUser()
        mContext = this
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideLoading() {

    }

    fun showLoading() {
        hideLoading()
    }

    fun displayLog(tag : String, msg : String){
        Log.d(tag, msg)
    }
    fun getViewDataBinding(): T {
        return mViewDataBinding
    }
    fun geUser() : USER? {
        return user
    }
    private fun setUser(){
        user =  USER()
    }


    private fun performDataBinding(){
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = mViewModel ?: getViewModel()
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

}