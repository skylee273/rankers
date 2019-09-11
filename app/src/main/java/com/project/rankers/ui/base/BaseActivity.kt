package com.project.rankers.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.LayoutRes
import android.view.inputmethod.InputMethodManager
import com.project.rankers.data.remote.response.UserRepo
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>>:  AppCompatActivity(), BaseFragment.Callback{

    private val mProgressDialog: Process? = null
    lateinit var mViewDataBinding: T
    private var mViewModel: V? = null
    lateinit var mContext: Context
    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getBindingVariable(): Int
    abstract fun getViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
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

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun performDataBinding(){
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = mViewModel ?: getViewModel()
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

}