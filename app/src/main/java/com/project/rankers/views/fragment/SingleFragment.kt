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
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.databinding.FragmentSingleBinding
import com.project.rankers.model.User
import com.project.rankers.retrofit.`interface`.Single
import com.project.rankers.retrofit.crater.RankersPostCreator
import com.project.rankers.retrofit.models.RankersServerRepo
import com.project.rankers.viewmodels.SingleViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingleFragment : Fragment() {

    private lateinit var singleBinding: FragmentSingleBinding
    private val viewModel = SingleViewModel()
    var rankersServerRepo: RankersServerRepo? = null
    var user: User? = null
    lateinit var mContext : Context
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        singleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_single, container, false)
        singleBinding.setVariable(BR.singleViewModel,viewModel)
        singleBinding.setVariable(BR.singleActivity,this)
        user = User()
        return singleBinding.root
    }
    fun regitClick(){
        createSingleRecord(user!!.geteMail(), singleBinding.editOther.text.toString(), singleBinding.editDate.text.toString(), singleBinding.editLocation.text.toString(),
                singleBinding.editResult.text.toString(), singleBinding.editWin.text.toString(), singleBinding.editLose.text.toString())
    }
    private fun createSingleRecord(email : String? , other : String?, date : String? , location : String?, result : String?, win : String?, lose : String?){
        val server = RankersPostCreator.create(Single::class.java)
        server.postSingleCreator(email, other, date, location, result, win, lose).enqueue(object : Callback<RankersServerRepo> {
            override fun onFailure(call: Call<RankersServerRepo>, t: Throwable) {
                Log.d("RECORD", t.message.toString())
            }
            override fun onResponse(call: Call<RankersServerRepo>, response: Response<RankersServerRepo>) {
                rankersServerRepo = response.body()
                if (rankersServerRepo != null) {
                    if (rankersServerRepo!!.getSuccess()) {
                        Toast.makeText(mContext, "성공적으로 등록되었습니다", Toast.LENGTH_LONG).show()
                    } else {

                    }
                }
            }
        })
    }
}