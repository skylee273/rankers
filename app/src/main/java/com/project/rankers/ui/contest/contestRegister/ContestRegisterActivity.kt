package com.project.rankers.ui.contest.contestRegister

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.files.fileChooser
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.model.db.DepartItem
import com.project.rankers.databinding.ActivityContestRegisterBinding
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject


class ContestRegisterActivity : BaseActivity<ActivityContestRegisterBinding, ContestRegisterViewModel>(), ContestRegisterNavigator, ContestRegisterAdapter.ContestRegisterAdapterListener{



    @Inject
    internal var contestRegisterAdapter : ContestRegisterAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private lateinit var contestRegisterBinding: ActivityContestRegisterBinding
    private var mContestRegisterViewModel: ContestRegisterViewModel? = null
    private var mLayoutManager = LinearLayoutManager(this)

    override fun showFaildDialog() {
        runOnUiThread {
            MaterialDialog(this).show {
                title(R.string.register_title)
                message(R.string.register_fail_message)
                positiveButton { R.string.agree }
            }
        }
    }

    override fun showDepartDialog(hint: String) {
        this.setTheme(R.style.DialogTeme)
        MaterialDialog(this).show {
            title(R.string.title)
            input(
                    hint = "부서를 입력해주세요"
            ) { _, text ->
                mContestRegisterViewModel!!.arrayDepart.add(DepartItem("$text"))
                contestRegisterAdapter!!.addItems(DepartItem("$text"))
            }
            positiveButton { R.string.agree }
            negativeButton(R.string.disagree)
        }
    }

    override fun removeClick(position: Int) {
        mContestRegisterViewModel!!.arrayDepart.removeAt(position)
        contestRegisterAdapter!!.removeItems(position)
    }

    override fun onRetryClick() {
       //
    }

    override fun showFileDialog() {

        MaterialDialog(mContext).show {
            fileChooser(
                    allowFolderCreation = true,
                    folderCreationLabel = R.string.new_folder // optional as well
            ) { dialog, file ->

            }
        }
    }

    override fun showDateDialog(type: Int) {
        val cal: Calendar? = Calendar.getInstance()
        when (type) {
            0 -> {
                val dialog = DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { _, year, month, date ->
                    val time = String.format("%04d.%02d.%02d", year, month + 1, date)
                    mContestRegisterViewModel!!.date.set(time)
                }, cal!!.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
                dialog.show()
            }
            1 -> {
                val dialog = DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { _, year, month, date ->
                    val time = String.format("%04d.%02d.%02d", year, month + 1, date)
                    mContestRegisterViewModel!!.endDate.set(time)
                }, cal!!.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
                dialog.show()
            }
        }
    }

    override fun showTypeDialog(items: List<String>) {
        var type: String? = null
        mContext.setTheme(R.style.DialogTeme)
        MaterialDialog(mContext).show {
            title(R.string.type_title)
            listItemsSingleChoice(items = items) { _, _, text ->
                type = text
            }
            onDismiss {
                mContestRegisterViewModel!!.type.set(type)
            }
            positiveButton(R.string.agree) {}
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_contest_register
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ContestRegisterViewModel {
        mContestRegisterViewModel = ViewModelProviders.of(this, factory).get(ContestRegisterViewModel::class.java)
        return mContestRegisterViewModel as ContestRegisterViewModel
    }

    override fun showDialog(title: String, message: String) {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = title)
                message(text = message)
                positiveButton(text = "확인"){
                    finish()
                }
            }
        }
    }

    override fun handleError(throwable: Throwable) {
        displayLog("ContestRegisterActivity", throwable.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contestRegisterBinding = getViewDataBinding()
        mContestRegisterViewModel!!.navigator = this
        setUp()
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = contestRegisterBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        contestRegisterAdapter = ContestRegisterAdapter(ArrayList())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        contestRegisterBinding.recycler.layoutManager = mLayoutManager
        contestRegisterBinding.recycler.itemAnimator = DefaultItemAnimator()
        contestRegisterBinding.recycler.setHasFixedSize(true)
        contestRegisterBinding.recycler.adapter = contestRegisterAdapter
        contestRegisterAdapter!!.setListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

