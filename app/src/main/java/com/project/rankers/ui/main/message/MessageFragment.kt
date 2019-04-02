package com.project.rankers.ui.main.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.rankers.R

class MessageFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View? = inflater.inflate(R.layout.fragment_msg, container, false)

        return view
    }
}