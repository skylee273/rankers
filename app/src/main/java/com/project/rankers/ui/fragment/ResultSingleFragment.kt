package com.project.rankers.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.adapter.SingleAdapter
import com.project.rankers.databinding.FragmentResultSingleBinding
import com.project.rankers.model.USER
import com.project.rankers.retrofit.api.Api
import com.project.rankers.retrofit.models.SingleRepo
import com.project.rankers.viewmodels.ResultSingleViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ResultSingleFragment : Fragment(){
    private lateinit var resultSingleBinding: FragmentResultSingleBinding
    private val viewModel= ResultSingleViewModel()
    lateinit var mContext : Context
    lateinit var compositeDisposable: CompositeDisposable
    private var linearLayoutManager = LinearLayoutManager(activity)
    var user: USER? = null
    lateinit var singleAdapter : SingleAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        user = USER()
        resultSingleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_single, container, false)
        resultSingleBinding.setVariable(BR.singleViewModel,viewModel)
        resultSingleBinding.setVariable(BR.singleFragment,this)

        resultSingleBinding.recycler.setHasFixedSize(true)
        resultSingleBinding.recycler.layoutManager = linearLayoutManager

        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getSingleRepoList(user!!.geteMail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: SingleRepo ->
                    singleAdapter = SingleAdapter(this.activity!!, response.items)
                    resultSingleBinding.recycler.adapter = singleAdapter
                }, { error: Throwable ->
                    Log.d("Single", error.localizedMessage)
                }))

        return resultSingleBinding.root
    }
    // 콜백터리를 일괄처리
    // 비동기 콜백처리가 완료되 결과과 반환될때 화면이 종료되면 Crash 현상 방지
    // , 메모리 릭을 발생하지 않게 하기 위해 clear 메소드를 호출함으로써 콜백을 해제 할 수 있다.
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}