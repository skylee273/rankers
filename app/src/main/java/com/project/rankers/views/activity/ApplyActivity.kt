package com.project.rankers.views.activity

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.adapter.ApplayAdapter
import com.project.rankers.databinding.ActivityApplyBinding
import com.project.rankers.viewmodels.ApplyViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.collections.ArrayList

class ApplyActivity : AppCompatActivity() {

    private lateinit var applyBinding: ActivityApplyBinding
    private val  viewModel =  ApplyViewModel()
    lateinit var mContext: Context
    lateinit var compositeDisposable: CompositeDisposable
    private var linearLayoutManager = LinearLayoutManager(this)
    private lateinit var applayAdapter : ApplayAdapter
    lateinit var contestArray : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        applyBinding = DataBindingUtil.setContentView(this, R.layout.activity_apply)
        applyBinding.setVariable(BR.activity, this)
        applyBinding.setVariable(BR.viewModel, viewModel)

        compositeDisposable = CompositeDisposable()

        val mToolbar = applyBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        applyBinding.recycler.setHasFixedSize(true)
        applyBinding.recycler.layoutManager = linearLayoutManager


    }

    @SuppressLint("SetTextI18n")
    private fun setUI(){
        val intent = intent
        contestArray = intent.extras.getStringArray("contest")!!
        if(contestArray.isNotEmpty()){
            applyBinding.textTitle.text = "대회명 : " + contestArray[0]
            applyBinding.textDate.text = "대회기간 : " +  contestArray[1]
            applyBinding.textLocation.text = "대회장소 : " +  contestArray[2]
            applyBinding.textHost.text = "주관기관 : " + contestArray[3]
        }
        val list = contestArray[4].split(",")
        applayAdapter = ApplayAdapter(this, list)
        setClickListner()
        applyBinding.recycler.adapter = applayAdapter

    }

    private fun setClickListner(){
        applayAdapter.itemClick = object: ApplayAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(applicationContext, ApplicationActivity::class.java)
                val depart = applayAdapter.getDepart(position)
                intent.putExtra("depart",depart)
                intent.putExtra("id",contestArray[5])
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
    override fun onResume() {
        super.onResume()
        setUI()
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