package com.project.rankers.views.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.adapter.ContestAdapter
import com.project.rankers.databinding.ActivityOperationBinding
import com.project.rankers.model.USER
import com.project.rankers.retrofit.api.Api
import com.project.rankers.retrofit.items.ContestItem
import com.project.rankers.retrofit.models.ContestRepo
import com.project.rankers.viewmodels.OperationViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class OperationActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var operationBinding: ActivityOperationBinding
    private val viewModel = OperationViewModel()
    private var linearLayoutManager = LinearLayoutManager(this)
    lateinit var compositeDisposable: CompositeDisposable
    private var contestAdapter : ContestAdapter? = null

    val email: String
        get() = user!!.geteMail()

    var user: USER? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        user = USER()
        operationBinding = DataBindingUtil.setContentView(this, R.layout.activity_operation)
        operationBinding.setVariable(BR.viewModel, viewModel)
        operationBinding.setVariable(BR.activity, this)

        val mToolbar = operationBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        operationBinding.recycler.setHasFixedSize(true)
        operationBinding.recycler.layoutManager = linearLayoutManager

        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getOperationList(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: ContestRepo ->
                    contestAdapter = ContestAdapter(this, response.items)
                    setClickListner()
                    operationBinding.recycler.adapter = contestAdapter
                }) {
                    Log.e("MyTag", "${it.message}")
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

    private fun setClickListner(){
        contestAdapter!!.itemClick = object: ContestAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val contestItem : ContestItem = contestAdapter!!.itemClick(position)
                val intent = Intent(applicationContext, RoundActivity::class.java)
                val id = contestItem.id
                val depart = contestItem.depart
                intent.putExtra("id",id)
                intent.putExtra("depart",depart)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


}