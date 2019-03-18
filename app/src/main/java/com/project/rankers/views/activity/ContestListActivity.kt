package com.project.rankers.views.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.adapter.ContestAdapter
import com.project.rankers.databinding.ActivityContestBinding
import com.project.rankers.retrofit.api.Api
import com.project.rankers.retrofit.items.ContestItem
import com.project.rankers.retrofit.models.ContestRepo
import com.project.rankers.viewmodels.ContestListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import android.content.Intent





class ContestListActivity  : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var contestListBinding: ActivityContestBinding
    private val viewModel = ContestListViewModel()
    private var linearLayoutManager = LinearLayoutManager(this)
    private var contestAdapter : ContestAdapter? = null
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        contestListBinding = DataBindingUtil.setContentView(this, R.layout.activity_contest)
        contestListBinding.setVariable(BR.contestListModel, viewModel)
        contestListBinding.setVariable(BR.contestListActivity, this)

        val mToolbar = contestListBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        contestListBinding.recycler.setHasFixedSize(true)
        contestListBinding.recycler.layoutManager = linearLayoutManager

        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getContestList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: ContestRepo ->
                    contestAdapter = ContestAdapter(this, response.items)
                    setClickListner()
                    contestListBinding.recycler.adapter = contestAdapter
                }) {
                    // 에러블록
                    Log.e("MyTag", "${it.message}")
                })



    }
    private fun setClickListner(){
        contestAdapter!!.itemClick = object: ContestAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val contestItem : ContestItem = contestAdapter!!.itemClick(position)

                val intent = Intent(applicationContext, ApplyActivity::class.java)
                val array = arrayOf(contestItem.title, contestItem.start + " ~ "+ contestItem.end, contestItem.location, contestItem.host, contestItem.depart, contestItem.id)
                intent.putExtra("contest",array)
                startActivity(intent)
                Toast.makeText(applicationContext, contestItem.location, Toast.LENGTH_SHORT).show()
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}