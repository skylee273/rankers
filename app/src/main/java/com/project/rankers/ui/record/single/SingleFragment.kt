package com.project.rankers.ui.record.single

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.databinding.FragmentSingleBinding
import com.project.rankers.ui.base.BaseFragment
import java.util.*
import javax.inject.Inject

class SingleFragment : BaseFragment<FragmentSingleBinding, SingleViewModel>(), SingleNavigator {


    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var singleViewModel: SingleViewModel? = null
    private lateinit var singleBinding: FragmentSingleBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_single
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): SingleViewModel {
        singleViewModel = ViewModelProviders.of(this, factory).get(SingleViewModel::class.java)
        return singleViewModel as SingleViewModel
    }

    override fun showDateDialog() {
        val cal: Calendar? = Calendar.getInstance()
        val dialog = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, month, date ->
            val time = String.format("%4d.%2d.%2d", year, month + 1, date)
            singleViewModel!!.date.set(time)
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
        displayLog("SingleFragment", throwable.toString())
    }
    override fun showSuccessDialog() {
        activity!!.runOnUiThread {
            Toast.makeText(context, "경기가 등록되었습니다.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        singleViewModel!!.navigator = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singleBinding = this.viewDataBinding!!

    }

    companion object {
        fun newInstance(): SingleFragment {
            val args = Bundle()
            val fragment = SingleFragment()
            fragment.arguments = args
            return fragment
        }
    }
}