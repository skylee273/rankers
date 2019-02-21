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
import com.project.rankers.adapter.ContestLocalAdapter
import com.project.rankers.databinding.FragmentContestBinding
import com.project.rankers.model.KTA
import com.project.rankers.model.LOCAL
import com.project.rankers.retrofit.api.Api
import com.project.rankers.retrofit.models.ContestRepo
import com.project.rankers.views.activity.ContestRegisterActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ContestFragement : Fragment() {

    private var Kta = ArrayList<KTA>()
    private var local = ArrayList<LOCAL>()
    private var KtaLinearLayoutManager = LinearLayoutManager(activity)
    private var LocalLinearLayoutManager = LinearLayoutManager(activity)
    lateinit var compositeDisposable: CompositeDisposable


    private lateinit var contestBinding: FragmentContestBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contest, container, false)
        contestBinding.setVariable(BR.main, this)


        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getContestList()
                .subscribeOn(Schedulers.newThread())
                .take(4)
                .subscribe({ response: ContestRepo ->
                    for(item in response.items){
                        val type = item.type
                        if(type.equals(" KTA "))
                            Kta.add(KTA(item.type, item.start + " ~ " + item.end, item.title, item.location))
                        if(item.type == " LOCAL ")
                            local.add(LOCAL(item.type, item.start + " ~ " + item.end, item.title, item.location))
                    }
                }) {
                    // 에러블록
                    Log.e("MyTag", "${it.message}")
                })

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
        compositeDisposable.dispose()
    }

    // 액티비티 이동
    fun registerContest() {
        val nextIntent = Intent(context, ContestRegisterActivity::class.java)
        startActivity(nextIntent)
    }
}