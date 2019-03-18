package com.project.rankers.views.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.rankers.R
import com.project.rankers.databinding.FragmentResultContestBinding
import com.project.rankers.model.USER
import io.reactivex.disposables.CompositeDisposable

class ResultContestFragment : Fragment() {
    private lateinit var resultContestBinding: FragmentResultContestBinding
    lateinit var mContext : Context
    lateinit var compositeDisposable: CompositeDisposable
    private var linearLayoutManager = LinearLayoutManager(activity)
    var user: USER? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        user = USER()
        resultContestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_contest, container, false)
        resultContestBinding.setVariable(BR.fragment,this)

        compositeDisposable = CompositeDisposable()

        return resultContestBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}