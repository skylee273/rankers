package com.project.rankers.ui.contest.operation.manager

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
import com.project.rankers.data.remote.response.UserRepo
import com.project.rankers.databinding.FragmentMangerBinding
import com.project.rankers.ui.base.BaseFragment
import java.util.*
import javax.inject.Inject

class ManagerFragment  : BaseFragment<FragmentMangerBinding, ManagerViewModel>(), ManagerNavigator, ManagerAdapter.ManagerAdapterListenr {


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(activity)
    private var manageViewModel: ManagerViewModel? = null
    private var manageAdapter: ManagerAdapter? = null

    private lateinit var mangerBinding: FragmentMangerBinding

    override fun showUserList(userItem: List<UserRepo.User>) {
        manageAdapter!!.addItems(userItem)
    }

    override fun failDialog() {
        MaterialDialog(context!!).show {
            title(text = "매니저 등록")
            message(text = "매니저를 등록하는데 실패하였습니다.")
            positiveButton { R.string.agree }
        }

    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_manger
    }

    override fun getViewModel(): ManagerViewModel {
        manageViewModel = ViewModelProviders.of(this, factory).get(ManagerViewModel::class.java)
        return manageViewModel as ManagerViewModel
    }

    override fun handleError(throwable: Throwable) {
        displayLog("ManagerFragment", throwable.toString())
    }

    override fun onRetryClick() {
        manageViewModel!!.fetchCompetitions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manageViewModel!!.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mangerBinding = this.viewDataBinding!!
        mangerBinding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                val text = mangerBinding.editSearch.text.toString()
                        .toLowerCase(Locale.getDefault())
                manageAdapter!!.filter(text)
            }
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUp()
        refreshView()
    }

    fun refreshView() {
        manageViewModel!!.fetchCompetitions()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        if(mangerBinding.recycler.layoutManager != null){
            mangerBinding.recycler.layoutManager = null
        }

        manageAdapter = ManagerAdapter(ArrayList<UserRepo.User>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mangerBinding.recycler.layoutManager = LinearLayoutManager(context)
        mangerBinding.recycler.itemAnimator = DefaultItemAnimator()
        mangerBinding.recycler.setHasFixedSize(true)
        mangerBinding.recycler.adapter = manageAdapter
        manageAdapter!!.setListener(this)
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