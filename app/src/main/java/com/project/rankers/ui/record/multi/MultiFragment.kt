package com.project.rankers.ui.record.multi

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.FragmentMultiBinding
import com.project.rankers.ui.base.BaseFragment
import java.util.*
import javax.inject.Inject

class MultiFragment : BaseFragment<FragmentMultiBinding, MultiViewModel>(), MultiNavigator {

    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var multiViewModel: MultiViewModel? = null
    private lateinit var multiBinding: FragmentMultiBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_multi
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MultiViewModel {
        multiViewModel = ViewModelProviders.of(this, factory).get(MultiViewModel::class.java)
        return multiViewModel as MultiViewModel
    }

    override fun showDateDialog() {
        val cal: Calendar? = Calendar.getInstance()

        val dialog = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, month, date ->
            val time = String.format("%4d.%2d.%2d", year, month + 1, date)
            multiViewModel!!.date.set(time)
        }, cal!!.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
        dialog.datePicker.maxDate = Date().time
        dialog.show()
    }

    override fun showFaildDialog() {
        MaterialDialog(context!!).show {
            title(text = "경기등록 실패")
            message(text = "정보를 모두 입력하세요.")
            positiveButton { R.string.agree }
        }
    }

    override fun handleError(throwable: Throwable) {
        displayLog("MultiFragment", throwable.toString())
    }

    override fun showSuccessDialog() {
        activity!!.runOnUiThread {
            MaterialDialog(context!!).show {
                title(text = "기록등록")
                message(text = "복식기록을 등록하였습니다.")
                positiveButton (text = "확인"){
                    activity!!.finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multiViewModel!!.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        multiBinding = this.viewDataBinding!!

    }

    companion object {
        fun newInstance(): MultiFragment {
            val args = Bundle()
            val fragment = MultiFragment()
            fragment.arguments = args
            return fragment
        }
    }
}