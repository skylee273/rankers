package com.project.rankers.ui.operation.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.project.rankers.R
import com.project.rankers.adapter.DashBoardAdapter
import com.project.rankers.databinding.FragmentResultContestBinding
import com.project.rankers.data.model.db.USER
import com.project.rankers.ui.leagueResult.LeagueResultActivity
import io.reactivex.disposables.CompositeDisposable

class ResultContestFragment : Fragment() {
    private lateinit var resultContestBinding: FragmentResultContestBinding
    lateinit var mContext: Context
    lateinit var compositeDisposable: CompositeDisposable
    private var linearLayoutManager = LinearLayoutManager(activity)
    private var param1: String = ""
    private var param2: String = ""
    private val myItems = listOf("예선전", "토너먼트")
    private var dashboardAdapter: DashBoardAdapter? = null

    var user: USER? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (arguments != null) {
            param1 = arguments!!.getString("CONTEST_ID") // 전달한 key 값
            param2 = arguments!!.getString("CONTEST_DEPART") // 전달한 key 값
        }
        mContext = this.activity!!
        user = USER()
        resultContestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result_contest, container, false)
        resultContestBinding.setVariable(BR.fragment, this)

        resultContestBinding.recycler.setHasFixedSize(true)
        resultContestBinding.recycler.layoutManager = linearLayoutManager

        dashboardAdapter = DashBoardAdapter(this.activity!!, param2.split(","))
        setClickListner()
        resultContestBinding.recycler.adapter = dashboardAdapter

        return resultContestBinding.root
    }

    private fun setClickListner() {
        dashboardAdapter!!.itemClick = object : DashBoardAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val value = dashboardAdapter!!.getDeaprt(position)
                var type = 0
                MaterialDialog(activity!!).show {
                    title(text = "대진표작성")
                    listItemsSingleChoice(items = myItems) { dialog, index, text ->
                        type = index
                    }
                    positiveButton(R.string.agree) {
                        when (type) {
                            0 -> {
                                Log.d("대진표 번호", "예선")
                                val intent = Intent(mContext, LeagueResultActivity::class.java)
                                intent.putExtra("CONTEST_DEPART", value)
                                intent.putExtra("CONTEST_ID", param1)
                                startActivity(intent)
                            }
                            1 -> {
                                Log.d("대진표 번호", "본선")
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(): ResultContestFragment {
            val args = Bundle()
            val fragment = ResultContestFragment()
            fragment.arguments = args
            return fragment
        }
    }
}