package com.project.rankers.ui.contest.apply

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.ActivityApplyBinding
import com.project.rankers.ui.base.BaseActivity
import com.project.rankers.ui.contest.form.ApplicationFormActivity
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class ApplyActivity : BaseActivity<ActivityApplyBinding, ApplyViewModel>(), ApplyNavigator {


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private lateinit var applyBinding: ActivityApplyBinding
    private var  applyViewModel : ApplyViewModel? = null
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var contestArray : Array<String>
    private lateinit var arrayAdapter : ArrayAdapter<String>

    override fun getLayoutId(): Int {
       return R.layout.activity_apply
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ApplyViewModel {
        applyViewModel = ViewModelProviders.of(this, factory).get(ApplyViewModel::class.java)
        return applyViewModel as ApplyViewModel
    }
    override fun openFormActivity(departName: String, contestID: String) {
        val intent = Intent(applicationContext, ApplicationFormActivity::class.java)
        intent.putExtra("departName",departName)
        intent.putExtra("id",contestID)
        startActivity(intent)
    }
    override fun handleError(error: String) {
        displayLog("ApplyActivity", error)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyBinding = getViewDataBinding()
        applyViewModel!!.navigator = this
        setUP()
    }

    @SuppressLint("SetTextI18n")
    private fun setUP(){
        val mToolbar = applyBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val intent = intent
        contestArray = intent.extras.getStringArray("contest")!!
        if(contestArray.isNotEmpty()){
            applyViewModel!!.setApply(contestArray[0], contestArray[1], contestArray[2], contestArray[3], contestArray[5])
        }
        val list = contestArray[4].split(",")
        arrayAdapter = ArrayAdapter(applicationContext, R.layout.custom_simple_dropdown_item_1line, list)
        applyBinding.spinner.adapter = arrayAdapter
        applyBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                applyViewModel!!.departName = list[position]
                applyViewModel!!.isDepart = true
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                applyViewModel!!.isDepart = false
            }
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


}