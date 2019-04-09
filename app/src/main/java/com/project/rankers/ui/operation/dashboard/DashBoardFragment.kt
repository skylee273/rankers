package com.project.rankers.ui.operation.dashboard

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
import com.project.rankers.databinding.FragmentDashboardBinding
import com.project.rankers.ui.league.LeagueActivity
import com.project.rankers.ui.tournament.TournamentActivity

class DashBoardFragment : Fragment() {
    private lateinit var dashboardBinding: FragmentDashboardBinding
    lateinit var mContext: Context
    private var linearLayoutManager = LinearLayoutManager(activity)
    private var dashboardAdapter: DashBoardAdapter? = null
    private var param1: String = ""
    private var param2: String = ""
    private val myItems = listOf("예선전", "토너먼트")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (arguments != null) {
            param1 = arguments!!.getString("CONTEST_ID") // 전달한 key 값
            param2 = arguments!!.getString("CONTEST_DEPART") // 전달한 key 값
        }
        mContext = this.activity!!
        dashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        dashboardBinding.setVariable(BR.fragment, this)

        dashboardBinding.recycler.setHasFixedSize(true)
        dashboardBinding.recycler.layoutManager = linearLayoutManager

        dashboardAdapter = DashBoardAdapter(this.activity!!, param2.split(","))
        setClickListner()
        dashboardBinding.recycler.adapter = dashboardAdapter

        return dashboardBinding.root
    }


    private fun setClickListner() {
        dashboardAdapter!!.itemClick = object : DashBoardAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val deart = dashboardAdapter!!.getDeaprt(position)
                MaterialDialog(activity!!).show {
                    title(text = "대진표작성")
                    listItemsSingleChoice(items = myItems) { dialog, index, text ->
                        when (index) {
                            0 -> {
                                Log.d("대진표 번호", "예선")
                                val intent = Intent(mContext, LeagueActivity::class.java)
                                intent.putExtra("CONTEST_DEPART", deart)
                                intent.putExtra("CONTEST_ID", param1)
                                startActivity(intent)
                            }
                            1 -> {
                                Log.d("대진표 번호", "본선")
                                val intent = Intent(mContext, TournamentActivity::class.java)
                                intent.putExtra("CONTEST_DEPART", deart)
                                intent.putExtra("CONTEST_ID", param1)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }
    companion object {
        fun newInstance(): DashBoardFragment {
            val args = Bundle()
            val fragment = DashBoardFragment()
            fragment.arguments = args
            return fragment
        }
    }


}