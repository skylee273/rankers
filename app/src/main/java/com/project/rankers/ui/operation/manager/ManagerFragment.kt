package com.project.rankers.ui.operation.manager

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
import com.project.rankers.databinding.FragmentMangerBinding
import com.project.rankers.model.USER
import com.project.rankers.ui.operation.result.ResultContestFragment
import io.reactivex.disposables.CompositeDisposable

class ManagerFragment  : Fragment(){
    private lateinit var mangerBinding: FragmentMangerBinding
    lateinit var mContext : Context
    lateinit var compositeDisposable: CompositeDisposable
    private var linearLayoutManager = LinearLayoutManager(activity)
    var user: USER? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        user = USER()
        mangerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_manger, container, false)
        mangerBinding.setVariable(BR.fragment,this)

        compositeDisposable = CompositeDisposable()

        return mangerBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
    companion object {
        fun newInstance(): ManagerFragment {
            val args = Bundle()
            val fragment = ManagerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}