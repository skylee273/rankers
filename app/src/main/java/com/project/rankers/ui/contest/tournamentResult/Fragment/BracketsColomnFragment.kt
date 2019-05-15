package com.project.rankers.ui.contest.tournamentResult.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.project.rankers.R
import com.project.rankers.data.model.db.ColomnData
import com.project.rankers.data.model.db.MatchData
import com.project.rankers.ui.contest.tournamentResult.adapter.BracketsCellAdapter
import com.project.rankers.utils.TournamentUtility
import java.util.*

/**
 * Created by Emil on 21/10/17.
 */

class BracketsColomnFragment : Fragment() {

    private var sectionData: ArrayList<ColomnData>? = null
    private var colomnData: ColomnData? = null
    var sectionNumber = 0
        private set
    var previousBracketSize: Int = 0
        private set
    var colomnList: ArrayList<MatchData>? = null
        private set
    private var bracketsRV: RecyclerView? = null

    private var adapter: BracketsCellAdapter? = null

    val currentBracketSize: Int
        get() = colomnList!!.size


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_brackets_colomn, container, false)
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
            colomnList = ArrayList()
            colomnData = arguments!!.getSerializable("colomn_data") as ColomnData
            sectionData = arguments!!.getSerializable("data") as ArrayList<ColomnData>?
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

    fun getItem() :  ArrayList<ColomnData>?{
        return adapter!!.getAllItem()
    }

    @SuppressLint("WrongConstant")
    private fun initAdapter() {

        //        pBar.setVisibility(View.GONE);
        adapter = BracketsCellAdapter(this, context!!, colomnList, sectionData, sectionNumber)
        setClickListner()
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

    private fun setClickListner() {
        adapter!!.itemClick = object : BracketsCellAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                MaterialDialog(context!!).show {
                    title(text = "점수입력")
                    customView(R.layout.dialog_league, scrollable = true)
                    positiveButton(text = "확인") { dialog ->
                        try {
                            val winScore : EditText = dialog.getCustomView().findViewById(R.id.win_score)
                            val loseScore : EditText = dialog.getCustomView().findViewById(R.id.lose_score)
                            if(winScore.text.toString().toInt() == loseScore.text.toString().toInt()){
                                Toast.makeText(context, "무승부는 입력될수 없습니다.", Toast.LENGTH_SHORT).show()
                            }else{
                                adapter!!.modifyList(position,winScore.text.toString(), loseScore.text.toString())
                            }
                        }catch (e : NumberFormatException){
                            Toast.makeText(context, "스코어를 모두 입력하세요", Toast.LENGTH_SHORT).show()
                        }catch (e : Exception){
                           Log.d("Error", e.toString())
                        }
                    }
                    negativeButton(text = "취소")
                }
            }
        }
    }
    companion object {
        fun newInstance(): BracketsColomnFragment {
            val args = Bundle()
            val fragment = BracketsColomnFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
