package com.project.rankers.views.fragment

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import com.project.rankers.R
import com.project.rankers.adprer.ContestAdapter
import com.project.rankers.adprer.ContestLocalAdapter
import com.project.rankers.databinding.FragmentContestBinding
import com.project.rankers.model.KTA
import com.project.rankers.model.Local
import com.project.rankers.views.activity.ContestRegisterActivity
import io.reactivex.disposables.CompositeDisposable


class ContestFragement : Fragment() {

    private var Kta = ArrayList<KTA>()
    private var local = ArrayList<Local>()
    private var KtaLinearLayoutManager = LinearLayoutManager(activity)
    private var LocalLinearLayoutManager = LinearLayoutManager(activity)
    lateinit var compositeDisposable: CompositeDisposable


    private lateinit var contestBinding: FragmentContestBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contest, container, false)
        contestBinding.setVariable(BR.main,this)
        /**
         *
         *
         *   compositeDisposable = CompositeDisposable()

        compositeDisposable.add(RankersApi.getRepoList()
        .subscribeOn(Schedulers.newThread())
        .subscribe({ response: RankersResponseModel ->
        for (item in response.items) {
        Log.d("MainActivity", item.toString())
        }
        }, { error: Throwable ->
        Log.d("MainActivity", error.localizedMessage)
        Toast.makeText(activity, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
        }))
         */


        Kta.add(KTA("LOCAL", "2018.08.9~2018.01.21", "제 7회 순천 테니스 대회", "충청북도 청주시 흥덕구 가경동"))
        Kta.add(KTA("LOCAL", "2018.08.9~2018.01.21", "제 7회 순천 테니스 대회", "충청북도 청주시 흥덕구 가경동"))
        local.add(Local("LOCAL", "2018.08.9~2018.01.21", "제 7회 순천 테니스 대회", "충청북도 청주시 흥덕구 가경동"))

        contestBinding.rvContest.setHasFixedSize(true)
        contestBinding.rvContest2.setHasFixedSize(true)
        contestBinding.rvContest.layoutManager = KtaLinearLayoutManager
        contestBinding.rvContest2.layoutManager = LocalLinearLayoutManager
        contestBinding.rvContest.adapter = ContestAdapter(this.activity!!, Kta)
        contestBinding.rvContest2.adapter = ContestLocalAdapter(this.activity!!, local)
        //contestBinding.setVariable(BR.Kta)
        return contestBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        //compositeDisposable.dispose()
    }
    // 액티비티 이동
    fun registerContest(){
        val nextIntent = Intent(context, ContestRegisterActivity::class.java)
        startActivity(nextIntent)
    }
}