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
import com.project.rankers.R
import com.project.rankers.databinding.FragmentMultiBinding
import com.project.rankers.model.User
import com.project.rankers.retrofit.`interface`.Multi
import com.project.rankers.retrofit.`interface`.Single
import com.project.rankers.retrofit.crater.RankersPostCreator
import com.project.rankers.retrofit.models.RankersServerRepo
import com.project.rankers.viewmodels.MultiViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MultiFragment : Fragment() {

    private lateinit var multiBinding: FragmentMultiBinding
    private val viewModel = MultiViewModel()
    var rankersServerRepo: RankersServerRepo? = null
    var user: User? = null
    lateinit var mContext : Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = this.activity!!
        multiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_multi, container, false)
        multiBinding.setVariable(BR.multiViewModel,viewModel)
        multiBinding.setVariable(BR.multiActivity,this)
        user = User()
        return multiBinding.root
    }

    fun regitClick(){
        createSingleRecord(user!!.geteMail(), multiBinding.editPartner.text.toString(), multiBinding.editOther.text.toString(), multiBinding.editOtherpartner.text.toString(),
                multiBinding.editDate.text.toString(), multiBinding.editLocation.text.toString(), multiBinding.editResult.text.toString(),
                multiBinding.editWin.text.toString(), multiBinding.editLose.text.toString())
    }
    private fun createSingleRecord(email : String? , partner : String?, other : String?, otherPartner : String?, date : String? ,
                                   location : String?, result : String?, win : String?, lose : String?){
        val server = RankersPostCreator.create(Multi::class.java)
        server.postMultiCreator(email, partner, other, otherPartner, date, location, result, win, lose).enqueue(object : Callback<RankersServerRepo> {
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