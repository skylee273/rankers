package com.project.rankers.views.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.adprer.ContestAdapter
import com.project.rankers.adprer.ContestLocalAdapter
import com.project.rankers.adprer.SingleAdapter
import com.project.rankers.databinding.FragmentResultSingleBinding
import com.project.rankers.model.SINGLE
import com.project.rankers.model.User
import com.project.rankers.retrofit.api.RankersApi
import com.project.rankers.retrofit.models.RankersResponseModel
import com.project.rankers.retrofit.models.RankersServerRepo
import com.project.rankers.retrofit.models.SingleResponseModel
import com.project.rankers.viewmodels.ResultSingleViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ResultSingleFragment : Fragment(){
    private var Single = ArrayList<SINGLE>()
    private lateinit var resultSingleBinding: FragmentResultSingleBinding
    private val viewModel= ResultSingleViewModel()
    lateinit var mContext : Context
    lateinit var compositeDisposable: CompositeDisposable
    private var LinearLayoutManager = LinearLayoutManager(activity)
    var user: User? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        user = User()
        resultSingleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_single, container, false)
        resultSingleBinding.setVariable(BR.singleViewModel,viewModel)
        resultSingleBinding.setVariable(BR.singleFragment,this)

        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(RankersApi.getSingleRepoList(user!!.geteMail())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: SingleResponseModel ->
                    for (item in response.items) {
                        Single.add(SINGLE(item.other, item.date, item.location, item.result, item.win, item.lose))
                        Log.d("Single", item.toString())
                    }
                    (resultSingleBinding.recycler.adapter as SingleAdapter).notifyDataSetChanged()
                }, { error: Throwable ->
                    Log.d("Single", error.localizedMessage)
//                    Toast.makeText(activity, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                }))

        resultSingleBinding.recycler.setHasFixedSize(true)
        resultSingleBinding.recycler.layoutManager = LinearLayoutManager
        resultSingleBinding.recycler.adapter = SingleAdapter(this.activity!!, Single)



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