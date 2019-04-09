package com.project.rankers.ui.main.my

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.project.rankers.R
import com.project.rankers.databinding.FragmentMyBinding
import com.project.rankers.viewmodels.MyViewModel
import com.project.rankers.ui.personal.writing.WritingPersonalRecordActivity
import com.project.rankers.ui.privateResult.ResultActivity

class MyFragment : Fragment() {

    private lateinit var myBinding: FragmentMyBinding
    private val viewModel = MyViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        myBinding.setVariable(BR.myViewModel,viewModel)
        myBinding.setVariable(BR.myActivity,this)

        return myBinding.root
    }

    fun resultClick(){
        val intent = Intent(context, ResultActivity::class.java)
        startActivity(intent)
    }

    fun recordClick(){
        val intent = Intent(context, WritingPersonalRecordActivity::class.java)
        startActivity(intent)
    }

}