package com.project.rankers.ui.version

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.ActivityVersionBinding
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class VersionActivity : BaseActivity<ActivityVersionBinding, VersionViewModel>(), VersionNavigator {

    @Inject
    internal var factory : ViewModelProviderFactory? = null
    private lateinit var versionBinding : ActivityVersionBinding
    private var versionViewModel : VersionViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_version
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): VersionViewModel {
        versionViewModel = ViewModelProviders.of(this, factory).get(VersionViewModel::class.java)
        return versionViewModel as VersionViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        versionBinding = getViewDataBinding()
        versionViewModel!!.navigator = this

        setUp()
    }

    private fun setUp() {
        val mToolbar = versionBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        versionViewModel!!.fetchVersion()
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