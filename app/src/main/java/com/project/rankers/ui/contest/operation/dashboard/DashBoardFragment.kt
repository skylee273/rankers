package com.project.rankers.ui.contest.operation.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.FragmentDashboardBinding
import com.project.rankers.ui.base.BaseFragment
import com.project.rankers.ui.contest.league.LeagueActivity
import com.project.rankers.ui.contest.tournament.TournamentActivity
import javax.inject.Inject

class DashBoardFragment : BaseFragment<FragmentDashboardBinding, DashBoardViewModel>(), DashBoardNavigator, DashBoardAdapter.DashboardAdapterListener {


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var dashboardViewModel: DashBoardViewModel? = null
    private lateinit var dashboardBinding: FragmentDashboardBinding
    private var dashboardAdapter: DashBoardAdapter? = null
    private var mLayoutManager = LinearLayoutManager(activity)
    private var contestID: String? = null
    private var contestDepart: String? = null
    private val myItems = listOf("예선전", "토너먼트")
    private var depart: String? = null
    override fun handleError(throwable: Throwable) {
        displayLog("DashBoardFragment", throwable.toString())
    }

    override fun openTournamentActivity() {
        val intent = Intent(context, TournamentActivity::class.java)
        intent.putExtra("CONTEST_DEPART", depart)
        intent.putExtra("CONTEST_ID", contestID)
        startActivity(intent)
    }

    override fun openLeagueActivity() {
        val intent = Intent(context, LeagueActivity::class.java)
        intent.putExtra("CONTEST_DEPART", depart)
        intent.putExtra("CONTEST_ID", contestID)
        startActivity(intent)
    }


    override fun showDialog(title: String, message: String) {
        MaterialDialog(context!!).show {
            title(text = title)
            message(text = message)
            positiveButton(text = "확인")
        }
    }

    override fun onRetryClick() {
        //
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_dashboard
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): DashBoardViewModel {
        dashboardViewModel = ViewModelProviders.of(this, factory).get(DashBoardViewModel::class.java)
        return dashboardViewModel as DashBoardViewModel
    }

    override fun onItemClick(position: Int) {

        depart = dashboardAdapter!!.getPositionItem(position)
        MaterialDialog(activity!!).show {
            title(text = "대진표작성")
            listItemsSingleChoice(items = myItems, waitForPositiveButton = false) { dialog, index, text ->
                dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                positiveButton(text = "확인") {
                    when (index) {
                        0 -> {
                            dashboardViewModel!!.isLeague(contestID!!, depart!!)
                        }
                        1 -> {
                            dashboardViewModel!!.isTournament(contestID!!, depart!!)
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel!!.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            contestID = arguments!!.getString("CONTEST_ID")!! // 전달한 key 값
            contestDepart = arguments!!.getString("CONTEST_DEPART")!! // 전달한 key 값
        }
        dashboardBinding = this.viewDataBinding!!
        setUp()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        if(contestDepart!!.contains(",")){
            dashboardAdapter = DashBoardAdapter(contestDepart!!.split(",") as ArrayList<String>)
        }else{
            var arrayList : ArrayList<String>? = null
            arrayList = ArrayList()
            arrayList.add(contestDepart!!)
            dashboardAdapter = DashBoardAdapter(arrayList)
        }
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        dashboardBinding.recycler.layoutManager = mLayoutManager
        dashboardBinding.recycler.itemAnimator = DefaultItemAnimator()
        dashboardBinding.recycler.setHasFixedSize(true)
        dashboardBinding.recycler.adapter = dashboardAdapter
        dashboardAdapter!!.setListener(this)

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