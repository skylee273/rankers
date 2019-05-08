package com.project.rankers.ui.contest.form

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import androidx.core.text.set
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.remote.response.MatchRepo
import com.project.rankers.data.remote.response.UserRepo
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.databinding.ActivityApplicationFormBinding
import com.project.rankers.ui.match.MatchAdapter
import java.util.*
import javax.inject.Inject

class ApplicationFormActivity : BaseActivity<ActivityApplicationFormBinding, ApplicationFormViewModel>(), ApplicationFormNavigator, ApplicationFormAdapter.UserAdapterLisner {

    @Inject
    internal var applicationFormAdapter: ApplicationFormAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)

    private lateinit var applicationFormBinding: ActivityApplicationFormBinding
    private var mApplicationFormViewModel : ApplicationFormViewModel? = null

    override fun onRetryClick() {
        //
    }
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
                positiveButton(text = "확인"){
                    finish()
                }
            }
        }
    }
    override fun handleError(throwable: Throwable) {
        Log.e("form",throwable.message)
    }
    override fun showUserList(userItem: List<UserRepo.User>) {
        applicationFormAdapter!!.addItems(userItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationFormBinding = getViewDataBinding()
        mApplicationFormViewModel!!.navigator = this

        setContestInfo()
        setUp()
    }
    private fun setContestInfo(){
        val intent = intent
        mApplicationFormViewModel!!.setContestInfo(intent.extras.getString("id"),intent.extras.getString("departName"))
    }

    override fun onUpdatePartner(name: String, phone: String) {
        mApplicationFormViewModel!!.setPartner(name, phone)
    }


    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = applicationFormBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        applicationFormAdapter = ApplicationFormAdapter(ArrayList<UserRepo.User>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL

        applicationFormBinding.recycler.layoutManager = mLayoutManager
        applicationFormBinding.recycler.itemAnimator = DefaultItemAnimator()
        applicationFormBinding.recycler.setHasFixedSize(true)
        applicationFormBinding.recycler.adapter = applicationFormAdapter
        applicationFormAdapter!!.setListener(this)

        applicationFormBinding.editPartnerName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                val text = applicationFormBinding.editPartnerName.text.toString()
                        .toLowerCase(Locale.getDefault())
                applicationFormAdapter!!.filter(text)
            }
        })
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