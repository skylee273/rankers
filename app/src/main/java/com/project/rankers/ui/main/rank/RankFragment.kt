package com.project.rankers.ui.main.rank

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.remote.response.RankRepo
import com.project.rankers.databinding.FragmentRankBinding
import com.project.rankers.ui.base.BaseFragment
import java.util.*
import javax.inject.Inject

class RankFragment : BaseFragment<FragmentRankBinding, RankViewModel>(), RankNavigator, RankAdapter.RankAdapterListenr {


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(activity)
    private var rankViewModel: RankViewModel? = null
    private var rankAdapter: RankAdapter? = null

    private lateinit var rankBinding: FragmentRankBinding

    override fun showRankList(rankItem: List<RankRepo.Rank>) {
        rankAdapter!!.addItems(rankItem)
    }

    override fun failDialog() {
        MaterialDialog(context!!).show {
            title(text = "랭킹")
            message(text = "랭킹기록을 불러오는데 실패하였습니다.")
            positiveButton { R.string.agree }
        }

    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_rank
    }

    override fun getViewModel(): RankViewModel {
        rankViewModel = ViewModelProviders.of(this, factory).get(RankViewModel::class.java)
        return rankViewModel as RankViewModel
    }

    override fun handleError(throwable: Throwable) {
        displayLog("RankFragment", throwable.toString())
    }

    override fun onRetryClick() {
        rankViewModel!!.fetchCompetitions()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rankViewModel!!.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rankBinding = this.viewDataBinding!!
        rankBinding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                val text = rankBinding.editSearch.text.toString()
                        .toLowerCase(Locale.getDefault())
                rankAdapter!!.filter(text)
            }
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUp()
        refreshView()
    }

    fun refreshView() {
        rankViewModel!!.fetchCompetitions()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        if(rankBinding.recycler.layoutManager != null){
            rankBinding.recycler.layoutManager = null
        }

        rankAdapter = RankAdapter(ArrayList<RankRepo.Rank>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rankBinding.recycler.layoutManager = LinearLayoutManager(context)
        rankBinding.recycler.itemAnimator = DefaultItemAnimator()
        rankBinding.recycler.setHasFixedSize(true)
        rankBinding.recycler.adapter = rankAdapter
        rankAdapter!!.setListener(this)
    }


}