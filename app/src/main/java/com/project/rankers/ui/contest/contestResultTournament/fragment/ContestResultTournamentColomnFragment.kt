package com.project.rankers.ui.contest.contestResultTournament.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.project.rankers.R
import com.project.rankers.data.model.db.ColomnData
import com.project.rankers.data.model.db.MatchData
import com.project.rankers.utils.TournamentUtility

class ContestResultTournamentColomnFragment : Fragment() {

    private var colomnData: ColomnData? = null
    var sectionNumber = 0
        private set
    var previousBracketSize: Int = 0
        private set
    var colomnList: java.util.ArrayList<MatchData>? = null
        private set
    private var bracketsRV: RecyclerView? = null

    private var adapter: ContestResultTournamentAdapter? = null

    val currentBracketSize: Int
        get() = colomnList!!.size


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contest_result_tournament_colomn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getExtras()
        initAdapter()
    }

    private fun initViews() {

        bracketsRV = view!!.findViewById<View>(R.id.rv_score_board) as RecyclerView
    }

    private fun getExtras() {
        if (arguments != null) {
            colomnList = java.util.ArrayList()
            colomnData = arguments!!.getSerializable("colomn_data") as ColomnData
            sectionNumber = arguments!!.getInt("section_number")
            previousBracketSize = arguments!!.getInt("previous_section_size")
            colomnList!!.addAll(colomnData!!.matches)
            Log.d("colomnList!!.size", colomnList!!.size.toString())
            setInitialHeightForList()
        }
    }

    private fun setInitialHeightForList() {
        for (data in colomnList!!) {
            if (sectionNumber == 0) {
                data.height = TournamentUtility.dpToPx(131)
            } else if (sectionNumber == 1 && previousBracketSize != colomnList!!.size) {
                data.height = TournamentUtility.dpToPx(262)
            } else if (sectionNumber == 1 && previousBracketSize == colomnList!!.size) {
                data.height = TournamentUtility.dpToPx(131)
            } else if (previousBracketSize > colomnList!!.size) {
                data.height = TournamentUtility.dpToPx(262)
            } else if (previousBracketSize == colomnList!!.size) {
                data.height = TournamentUtility.dpToPx(131)
            }
        }

    }

    fun expandHeight(height: Int) {

        for (data in colomnList!!) {
            data.height = height
        }
        adapter!!.setList(colomnList!!)
    }

    fun shrinkView(height: Int) {
        for (data in colomnList!!) {
            data.height = height
        }
        adapter!!.setList(colomnList!!)
    }


    @SuppressLint("WrongConstant")
    private fun initAdapter() {
        adapter = ContestResultTournamentAdapter(this, context!!, colomnList)
        if (bracketsRV != null) {
            bracketsRV!!.setHasFixedSize(true)
            bracketsRV!!.isNestedScrollingEnabled = false
            bracketsRV!!.adapter = adapter
            bracketsRV!!.smoothScrollToPosition(0)
            val layoutManager = LinearLayoutManager(activity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            bracketsRV!!.layoutManager = layoutManager
            bracketsRV!!.itemAnimator = DefaultItemAnimator()
        }
    }

    companion object {
        fun newInstance(): ContestResultTournamentColomnFragment {
            val args = Bundle()
            val fragment = ContestResultTournamentColomnFragment()
            fragment.arguments = args
            return fragment
        }
    }
}