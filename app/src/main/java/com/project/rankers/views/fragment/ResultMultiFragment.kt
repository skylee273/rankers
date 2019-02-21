package com.project.rankers.views.fragment

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
import com.project.rankers.adapter.MutilAdapter
import com.project.rankers.databinding.FragmentResultMultiBinding
import com.project.rankers.model.MULTI
import com.project.rankers.model.USER
import com.project.rankers.retrofit.api.Api
import com.project.rankers.retrofit.models.MultiRepo
import com.project.rankers.viewmodels.ResultMultiViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ResultMultiFragment : Fragment() {
    private val arrayMulti = ArrayList<MULTI>()
    private lateinit var resultMultiBinding: FragmentResultMultiBinding
    private val viewModel = ResultMultiViewModel()
    lateinit var mContext : Context
    lateinit var compositeDisposable: CompositeDisposable
    private var LinearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
    var USER: USER? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        USER = USER()
        resultMultiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_multi, container, false)
        resultMultiBinding.setVariable(BR.multiViewModel,viewModel)
        resultMultiBinding.setVariable(BR.multiFragment,this)

        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getMultiRepoList(USER!!.geteMail())
                .subscribeOn(Schedulers.newThread())
                .doOnTerminate {
                    (resultMultiBinding.recycler.adapter as MutilAdapter).notifyDataSetChanged()
                }
                .subscribe({ response: MultiRepo ->
                    for (item in response.items) {
                        arrayMulti.add(MULTI(item.partner, item.other, item.otherpartner, item.date,item.result, item.win, item.lose))
                        Log.d("Multi", item.toString())
                    }
                }, { error: Throwable ->
                    Log.d("Multi", error.localizedMessage)
//                    Toast.makeText(activity, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                })

        )

        resultMultiBinding.recycler.setHasFixedSize(true)
        resultMultiBinding.recycler.layoutManager = LinearLayoutManager
        resultMultiBinding.recycler.adapter = MutilAdapter(this.activity!!, arrayMulti)



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