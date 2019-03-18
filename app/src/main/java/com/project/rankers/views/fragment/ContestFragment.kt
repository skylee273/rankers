package com.project.rankers.views.fragment

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import com.project.rankers.R
import com.project.rankers.adapter.ContestAdapter
import com.project.rankers.databinding.FragmentContestBinding
import com.project.rankers.retrofit.api.Api
import com.project.rankers.retrofit.models.ContestRepo
import com.project.rankers.views.activity.ContestListActivity
import com.project.rankers.views.activity.ContestRegisterActivity
import com.project.rankers.views.activity.OperationActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ContestFragment : Fragment() {

    private var KtaLinearLayoutManager = LinearLayoutManager(activity)
    lateinit var compositeDisposable: CompositeDisposable
    private var contestAdapter : ContestAdapter? = null


    private lateinit var contestBinding: FragmentContestBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contest, container, false)
        contestBinding.setVariable(BR.main, this)
        contestBinding.rvContest.setHasFixedSize(true)
        contestBinding.rvContest.layoutManager = KtaLinearLayoutManager

        return contestBinding.root
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getContestList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(4)
                .subscribe({ response: ContestRepo ->
                    contestAdapter = ContestAdapter(this.activity!!, response.items)
                    contestBinding.rvContest.adapter = contestAdapter
                }) {
                    Log.e("MyTag", "${it.message}")
                })
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    // 액티비티 이동
    fun registerContest() {
        val nextIntent = Intent(context, ContestRegisterActivity::class.java)
        startActivity(nextIntent)
    }
    fun nextApply() {
        val nextIntent = Intent(context, ContestListActivity::class.java)
        startActivity(nextIntent)
    }
    fun nextOperation(){
        val nextIntent = Intent(context, OperationActivity::class.java)
        startActivity(nextIntent)
    }

}