package com.project.rankers.ui.privateResult.fragment

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
import com.project.rankers.R
import com.project.rankers.databinding.FragmentSingleBinding
import com.project.rankers.data.model.db.USER
import com.project.rankers.data.remote.api.Api
import com.project.rankers.viewmodels.SingleViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SingleFragment : Fragment() {

    private lateinit var singleBinding: FragmentSingleBinding
    private val viewModel = SingleViewModel()
    var USER: USER? = null
    lateinit var mContext: Context
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        singleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_single, container, false)
        singleBinding.setVariable(BR.singleViewModel, viewModel)
        singleBinding.setVariable(BR.singleActivity, this)
        USER = USER()
        compositeDisposable = CompositeDisposable()
        return singleBinding.root
    }

    fun regitClick() {
        createSingleRecord(USER!!.geteMail(), singleBinding.editOther.text.toString(), singleBinding.editDate.text.toString(),
                singleBinding.editResult.text.toString(), singleBinding.editWin.text.toString(), singleBinding.editLose.text.toString())
    }

    private fun createSingleRecord(email: String?, other: String?, date: String?, result: String?, win: String?, lose: String?) {
        compositeDisposable.add(Api.postSingleCreator(email!!, other, date, result, win, lose)
                .subscribeOn(Schedulers.newThread())
                .subscribe({
                    if(it.getSuccess()){
                        Toast.makeText(mContext, "성공적으로 등록되었습니다", Toast.LENGTH_LONG).show()
                    }
                }) {
                    // 에러블록
                    Log.e("MyTag", "${it.message}")
                })
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}