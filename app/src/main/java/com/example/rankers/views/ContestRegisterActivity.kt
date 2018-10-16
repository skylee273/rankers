package com.example.rankers.views

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.databinding.library.baseAdapters.BR
import com.example.rankers.R
import com.example.rankers.databinding.ActivityContestRegisterBinding
import com.example.rankers.viewmodels.ContestRegisterViewModel

class ContestRegisterActivity : AppCompatActivity() {

    private lateinit var contestRegisterBinding: ActivityContestRegisterBinding
    lateinit var mContext: Context
    val viewModel = ContestRegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        contestRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_contest_register)
        contestRegisterBinding.setVariable(BR.rm, viewModel)

    }

}