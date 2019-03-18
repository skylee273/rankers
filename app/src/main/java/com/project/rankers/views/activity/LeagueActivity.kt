package com.project.rankers.views.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.project.rankers.R
import com.project.rankers.adapter.LeagueAdapter
import com.project.rankers.databinding.ActivityLeagueBinding
import com.project.rankers.model.LEAGUE
import com.project.rankers.retrofit.api.Api
import com.project.rankers.retrofit.models.ApplyRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class LeagueActivity : AppCompatActivity() {

    private lateinit var leagueBinding: ActivityLeagueBinding
    lateinit var mContext: Context
    lateinit var compositeDisposable: CompositeDisposable
    private var linearLayoutManager = LinearLayoutManager(this)
    lateinit var leagueAdapter: LeagueAdapter
    var count  = 1
    private var league = ArrayList<LEAGUE>()
    private var peopleItems = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        leagueBinding = DataBindingUtil.setContentView(this, R.layout.activity_league)
        leagueBinding.setVariable(BR.activity, this)

        val mToolbar = leagueBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        leagueBinding.recycler.setHasFixedSize(true)
        leagueBinding.recycler.layoutManager = linearLayoutManager


        setUI()

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


    private fun setClickListner(){
        leagueAdapter.itemClick = object: LeagueAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                if(peopleItems.size > 0){
                    showPeopleDialog(position)
                }else{
                    failDialog()
                }

            }
        }
    }

    private fun failDialog(){
        MaterialDialog(this).show {
            title(text = "선수")
            message(text = "선수인원이 없습니다.")
            positiveButton (text = "확인")
        }
    }

    private fun showPeopleDialog(po: Int){

        var list = listOf<String>()
        MaterialDialog(this).show {
            title(text = "선수명단")
            message(text = "최소 선수를 1 ~ 4명 선택하세요")
            listItemsMultiChoice(items = peopleItems , waitForPositiveButton = false) { dialog, index, text ->
                list = text
                if(list.size in 1..4) {
                    dialog.setActionButtonEnabled(WhichButton.POSITIVE, true)
                }else{
                    dialog.setActionButtonEnabled(WhichButton.POSITIVE, false)
                }
            }
            positiveButton (text = "확인"){
                refreshView(po, list.size, list)
            }
            negativeButton  (text = "취소")
        }
    }

    private fun refreshView(po : Int, cnt : Int,  list : List<String>){
        when(cnt){
            1 ->{
                league.set(po,(LEAGUE(po + 1,list[0],"2","3","4")))
                leagueAdapter.notifyDataSetChanged()
            }
            2 ->{
                league.set(po,(LEAGUE(po + 1,list[0],list[1],"3","4")))
                leagueAdapter.notifyDataSetChanged()
            }
            3 -> {
                league.set(po,(LEAGUE(po + 1,list[0],list[1],list[2],"4")))
                leagueAdapter.notifyDataSetChanged()
            }
            4 -> {
                league.set(po,(LEAGUE(po + 1,list[0],list[1],list[2],list[3])))
                leagueAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun setUI(){
        val intent = intent
        val id = intent.extras.getString("CONTEST_ID")
        val depart = intent.extras.getString("CONTEST_DEPART")
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(Api.getApplayList(id, depart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response: ApplyRepo ->
                    Log.d("Leage", response.toString())
                    leagueBinding.textCount.text = response.totalCount.toString()
                    for(people in response.items){
                        when(people.applyType.toInt()){
                            0 -> {
                                peopleItems.add(people.applyName)
                            }
                            1 -> {
                                peopleItems.add(people.applyName + "," + people.applyPartner)
                            }
                        }
                    }

                }) {
                    Log.e("MyTag", "${it.message}")
                })
    }
    fun sub(){
        if(count > 0){
            --count
            leagueBinding.textNumber.text = count.toString()
        }

    }
    fun plus(){
        ++count
        leagueBinding.textNumber.text = count.toString()
    }
    fun create(){
        for (num in 1 until  (count+1))
        {
            league.add(LEAGUE(num,"1","2","3","4"))
        }

        leagueAdapter = LeagueAdapter(this, league)
        setClickListner()
        leagueBinding.recycler.adapter = leagueAdapter
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