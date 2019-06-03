package com.project.rankers.ui.contest.operation.result

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
import com.project.rankers.databinding.FragmentResultContestBinding
import com.project.rankers.ui.base.BaseFragment
import com.project.rankers.ui.contest.leagueResult.LeagueResultActivity
import com.project.rankers.ui.contest.tournamentResult.TournamentResultActivity
import javax.inject.Inject

class ResultContestFragment : BaseFragment<FragmentResultContestBinding, ResultContestViewModel>(), ResultContestNavigator, ResultContestAdapter.ResultContestAdapterListener {

    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var resultContestViewModel: ResultContestViewModel? = null
    private lateinit var resultContestBinding: FragmentResultContestBinding
    private var resultContestAdapter: ResultContestAdapter? = null
    private var mLayoutManager = LinearLayoutManager(activity)
    private var contestID: String? = null
    private var contestDepart: String? = null
    private val myItems = listOf("예선전", "토너먼트")
    private var depart: String? = null

    override fun handleError(throwable: Throwable) {
        displayLog("ResultContestFragment", throwable.toString())
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
        return R.layout.fragment_result_contest
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ResultContestViewModel {
        resultContestViewModel = ViewModelProviders.of(this, factory).get(ResultContestViewModel::class.java)
        return resultContestViewModel as ResultContestViewModel
    }

    override fun onItemClick(position: Int) {
        depart = resultContestAdapter!!.getPositionItem(position)
        MaterialDialog(activity!!).show {
            title(text = "결과 작성")
            listItemsSingleChoice(items = myItems, waitForPositiveButton = false) { dialog, index, text ->
                dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                positiveButton(text = "확인") {
                    when (index) {
                        0 -> {
                            val intent = Intent(context, LeagueResultActivity::class.java)
                            intent.putExtra("CONTEST_DEPART", depart)
                            intent.putExtra("CONTEST_ID", contestID)
                            startActivity(intent)
                        }
                        1 -> {
                            val intent = Intent(context, TournamentResultActivity::class.java)
                            intent.putExtra("CONTEST_DEPART", depart)
                            intent.putExtra("CONTEST_ID", contestID)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultContestViewModel!!.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            contestID = arguments!!.getString("CONTEST_ID")!! // 전달한 key 값
            contestDepart = arguments!!.getString("CONTEST_DEPART")!! // 전달한 key 값
        }
        resultContestBinding = this.viewDataBinding!!
        setUp()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        resultContestAdapter = ResultContestAdapter(contestDepart!!.split(",") as ArrayList<String>)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        resultContestBinding.recycler.layoutManager = mLayoutManager
        resultContestBinding.recycler.itemAnimator = DefaultItemAnimator()
        resultContestBinding.recycler.setHasFixedSize(true)
        resultContestBinding.recycler.adapter = resultContestAdapter
        resultContestAdapter!!.setListener(this)

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