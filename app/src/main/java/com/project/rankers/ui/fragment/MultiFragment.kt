package com.project.rankers.ui.fragment

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
import com.project.rankers.databinding.FragmentMultiBinding
import com.project.rankers.data.model.db.USER
import com.project.rankers.data.remote.api.Api

import com.project.rankers.viewmodels.MultiViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MultiFragment : Fragment() {

    private lateinit var multiBinding: FragmentMultiBinding
    private val viewModel = MultiViewModel()
    var USER: USER? = null
    lateinit var mContext : Context
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        multiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_multi, container, false)
        multiBinding.setVariable(BR.multiViewModel,viewModel)
        multiBinding.setVariable(BR.multiActivity,this)
        USER = USER()
        compositeDisposable = CompositeDisposable()
        return multiBinding.root
    }

    fun regitClick(){
        createSingleRecord(USER!!.geteMail(), multiBinding.editPartner.text.toString(), multiBinding.editOther.text.toString(), multiBinding.editOtherpartner.text.toString(),
                multiBinding.editDate.text.toString(),  multiBinding.editResult.text.toString(),
                multiBinding.editWin.text.toString(), multiBinding.editLose.text.toString())
    }
    private fun createSingleRecord(email : String? , partner : String?, other : String?, otherPartner : String?, date : String? ,
                                    result : String?, win : String?, lose : String?){
        compositeDisposable.add(Api.postMultiCreator(email, partner, other, otherPartner, date, result, win, lose)
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
}