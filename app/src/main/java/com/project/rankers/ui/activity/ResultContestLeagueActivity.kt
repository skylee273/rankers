package com.project.rankers.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.project.rankers.R
import com.project.rankers.adapter.LeagueResultAdapter
import com.project.rankers.databinding.ActivityResultContestLeagueBinding
import com.project.rankers.model.LEAGUE_RESULT
import com.project.rankers.model.USER
import com.project.rankers.retrofit.api.Api
import com.project.rankers.retrofit.models.GroupRepo
import com.project.rankers.retrofit.models.LeagueRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class ResultContestLeagueActivity : AppCompatActivity() {
    private lateinit var resultContestLeagueBinding: ActivityResultContestLeagueBinding
    lateinit var mContext: Context
    lateinit var compositeDisposable: CompositeDisposable
    private var linearLayoutManager = LinearLayoutManager(this)
    lateinit var leagueAdapter: LeagueResultAdapter
    private var arrayLeague = ArrayList<LEAGUE_RESULT>()
    private lateinit var id: String
    private lateinit var depart: String
    private var myItem = listOf("A-B", "A-C", "A-D", "B-C", "B-D", "C-D")
    var user: USER? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        resultContestLeagueBinding = DataBindingUtil.setContentView(this, R.layout.activity_result_contest_league)
        resultContestLeagueBinding.setVariable(BR.activity, this)

        val mToolbar = resultContestLeagueBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        resultContestLeagueBinding.recycler.setHasFixedSize(true)
        resultContestLeagueBinding.recycler.layoutManager = linearLayoutManager

        val intent = intent
        id = intent.extras.getString("CONTEST_ID")
        depart = intent.extras.getString("CONTEST_DEPART")

        setUI()
    }

    private fun setUI() {
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getLeagueList(id, depart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: LeagueRepo ->
                    val items = response.items
                    refreshRecycler(items[0].leagueCount.toInt(), items[0].leagueMember.split("/"), items[0].leagueScore.split("/"))
                }) {
                    init()
                    Log.e("MyTag", "${it.message}")
                })
    }

    private fun init(){
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getGroupList(id, depart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: GroupRepo ->
                    val items = response.items
                    refreshRecycler(items[0].groupCount.toInt(), items[0].groupMember.split("/"))
                }) {
                    Log.e("MyTag", "${it.message}")
                })
    }
    private fun refreshRecycler(groupCount: Int, groupMember: List<String>, groupScore: List<String>) {
        for(index in 1..groupCount){
            val member = groupMember[index-1].split(",")
            val score = groupScore[index-1].split(",")
            arrayLeague.add(LEAGUE_RESULT(index,member[0], member[1], member[2], member[3], score[0], score[1], score[2],score[3], score[4], score[5]))
        }
        leagueAdapter = LeagueResultAdapter(this, arrayLeague)
        resultContestLeagueBinding.recycler.adapter = leagueAdapter
    }

    private fun refreshRecycler(groupCount: Int, groupMember: List<String>) {
        for(index in 1..groupCount){
            val member = groupMember[index-1].split(",")
            arrayLeague.add(LEAGUE_RESULT(index,member[0], member[1], member[2], member[3], "", "", "","", "", ""))
        }
        leagueAdapter = LeagueResultAdapter(this, arrayLeague)
        setClickListner()
        resultContestLeagueBinding.recycler.adapter = leagueAdapter
    }

    private fun setClickListner(){
        leagueAdapter!!.itemClick = object: LeagueResultAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val leagueItem : LEAGUE_RESULT = leagueAdapter!!.itemClick(position)
                showDialog(position,leagueItem)
            }
        }
    }

    private fun showDialog(po : Int, item : LEAGUE_RESULT) {
        var position  = 0
        MaterialDialog(this).show {
            title(text = "점수입력")
            message(text = "점수를 입력한 후 해당 위치를 클릭하세요.")
            customView(R.layout.dialog_league, scrollable = true)
            listItemsSingleChoice(items = myItem) { dialog, index, text ->
                position = index
            }
            positiveButton(text = "확인") { dialog ->
                val winScore : EditText = dialog.getCustomView().findViewById(R.id.win_score)
                val loseScore : EditText = dialog.getCustomView().findViewById(R.id.lose_score)
                when(position){
                    0 -> {
                        item.score1 = winScore.text.toString() + " : " + loseScore.text.toString()
                    }
                    1 -> {
                        item.score2 = winScore.text.toString() + " : " + loseScore.text.toString()
                    }
                    2 -> {
                        item.score3 = winScore.text.toString() + " : " + loseScore.text.toString()
                    }
                    3 -> {
                        item.score4 = winScore.text.toString() + " : " + loseScore.text.toString()
                    }
                    4 -> {
                        item.score5 = winScore.text.toString() + " : " + loseScore.text.toString()
                    }
                    5 -> {
                        item.score6 = winScore.text.toString() + " : " + loseScore.text.toString()
                    }

                }
                leagueAdapter.setItem(po, item)
            }
            negativeButton(text = "취소")
        }
    }


}