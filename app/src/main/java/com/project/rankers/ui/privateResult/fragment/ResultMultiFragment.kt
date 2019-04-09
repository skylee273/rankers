package com.project.rankers.ui.privateResult.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.project.rankers.R
import com.project.rankers.adapter.MultiAdapter
import com.project.rankers.databinding.FragmentResultMultiBinding
import com.project.rankers.data.model.db.USER
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.MultiRepo
import com.project.rankers.viewmodels.ResultMultiViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ResultMultiFragment : Fragment() {
    private lateinit var resultMultiBinding: FragmentResultMultiBinding
    private val viewModel = ResultMultiViewModel()
    lateinit var mContext : Context
    lateinit var compositeDisposable: CompositeDisposable
    private var linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
    private var user: USER? = null
    lateinit var multiAdapter : MultiAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        user = USER()
        resultMultiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_multi, container, false)
        resultMultiBinding.setVariable(BR.multiViewModel,viewModel)
        resultMultiBinding.setVariable(BR.multiFragment,this)

        resultMultiBinding.recycler.setHasFixedSize(true)
        resultMultiBinding.recycler.layoutManager = linearLayoutManager


        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getMultiRepoList(user!!.geteMail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: MultiRepo ->
                    multiAdapter = MultiAdapter(this.activity!!, response.items)
                    resultMultiBinding.recycler.adapter = multiAdapter
                }, { error: Throwable ->
                    Log.d("Multi", error.localizedMessage)
//                    Toast.makeText(activity, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                })

        )

        return resultMultiBinding.root
    }
    // 콜백터리를 일괄처리
    // 비동기 콜백처리가 완료되 결과과 반환될때 화면이 종료되면 Crash 현상 방지
    // , 메모리 릭을 발생하지 않게 하기 위해 clear 메소드를 호출함으로써 콜백을 해제 할 수 있다.
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}