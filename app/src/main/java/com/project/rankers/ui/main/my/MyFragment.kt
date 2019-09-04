package com.project.rankers.ui.main.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.project.rankers.R
import com.project.rankers.data.model.db.User
import com.project.rankers.databinding.FragmentMyBinding
import com.project.rankers.ui.match.MatchActivity
import com.project.rankers.ui.record.RecordActivity
import com.project.rankers.ui.version.VersionActivity

class MyFragment : Fragment() {

    private lateinit var myBinding: FragmentMyBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        myBinding.setVariable(BR.myActivity, this)

        myBinding.textName.text = User().userName
        myBinding.textEmail.text = User().userEmail
        return myBinding.root
    }

    fun resultClick() {
        val intent = Intent(context, MatchActivity::class.java)
        startActivity(intent)
    }

    fun recordClick() {
        val intent = Intent(context, RecordActivity::class.java)
        startActivity(intent)
    }

    fun versionClick() {
        val intent = Intent(context, VersionActivity::class.java)
        startActivity(intent)
    }

}