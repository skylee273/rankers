package com.project.rankers.ui.contest.tournamentResult.Fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.data.model.db.ColomnData
import com.project.rankers.data.model.db.CompetitorData
import com.project.rankers.data.model.db.MatchData
import com.project.rankers.data.model.db.User
import com.project.rankers.data.remote.api.Api
import com.project.rankers.data.remote.response.TournamentRepo
import com.project.rankers.ui.contest.tournamentResult.adapter.BracketsSectionAdapter
import com.project.rankers.ui.contest.tournamentResult.customviews.WrapContentHeightViewPager
import com.project.rankers.utils.TournamentUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Emil on 21/10/17.
 */

class BracketsFragment : Fragment(), ViewPager.OnPageChangeListener {

    private var viewPager: WrapContentHeightViewPager? = null
    private var uploadButton: Button? = null
    private var sectionAdapter: BracketsSectionAdapter? = null
    private var sectionList: ArrayList<ColomnData>? = null
    private var mNextSelectedScreen: Int = 0
    private val mCurrentPagerState: Int = 0
    lateinit var compositeDisposable: CompositeDisposable
    private var contestID: String = ""
    private var contestDepartName: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_brackts, container, false)
        if (arguments != null) {
            contestID = arguments!!.getString("CONTEST_ID")!! // 전달한 key 값
            contestDepartName = arguments!!.getString("CONTEST_DEPART")!! // 전달한 key 값
        }
        compositeDisposable = CompositeDisposable()

        uploadButton = rootView.findViewById(R.id.btn_upload)
        uploadButton!!.setOnClickListener {
            getUpload()
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        setServer()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun setServer() {
        compositeDisposable.add(Api.getTournamentList(contestID, contestDepartName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    setData(response)
                    intialiseViewPagerAdapter()
                }) {
                    Log.e("Error", it.toString())
                })
    }

    private fun setData(item: TournamentRepo) {
        sectionList = ArrayList()


        val tournamentItem = item.items
        val nextPow = tournamentItem[0].tournamentRound!!.toInt()
        val tournamentSize = logB(nextPow.toDouble(), 2.0) // 5
        //27
        val columnDataList = arrayOfNulls<ArrayList<MatchData>>(tournamentSize.toInt()) // 5
        val rowList = ArrayList<CompetitorData>() // 32
        val matchDataList = ArrayList<MatchData>() // 16

        var mod = 1
        var index = 0
        var rowIndex = 0
        var matchIndex = 0
        for (i in 0 until tournamentSize.toInt()) { // 0 ~ 4
            columnDataList[i] = ArrayList<MatchData>()
            for (j in 0 until (nextPow / mod) step 2) {     // 0 ~ 31
                rowList.add(CompetitorData(tournamentItem[index].tournamentPlayerOne, tournamentItem[index].tournamentScoreOne, (nextPow / mod).toString() + "강", tournamentItem[index].tournamentNumber)) // 28 개 넣음
                rowList.add(CompetitorData(tournamentItem[index].tournamentPlayerTwo, tournamentItem[index].tournamentScoreTwo, (nextPow / mod).toString() + "강", tournamentItem[index].tournamentNumber))
                index++
            }
            for (k in 0 until ((nextPow / 2) / mod)) {   // 0 ~ 16
                matchDataList.add(MatchData(rowList[rowIndex], rowList[rowIndex + 1])) // 14개 넣음
                columnDataList[i]!!.add(matchDataList[matchIndex])
                rowIndex += 2
                matchIndex++
            }
            mod *= 2
            sectionList!!.add(ColomnData(columnDataList[i]))
        }
    }

    private fun intialiseViewPagerAdapter() {

        sectionAdapter = BracketsSectionAdapter(childFragmentManager, this.sectionList!!)
        viewPager!!.offscreenPageLimit = 10
        viewPager!!.adapter = sectionAdapter
        viewPager!!.currentItem = 0
        viewPager!!.pageMargin = -200
        viewPager!!.isHorizontalFadingEdgeEnabled = true
        viewPager!!.setFadingEdgeLength(50)

        viewPager!!.addOnPageChangeListener(this)
        var position = 0
        //updateViewhere
        if (getBracketsFragment(position + 1)!!.currentBracketSize == getBracketsFragment(position + 1)!!.previousBracketSize) {
            getBracketsFragment(position + 1)!!.shrinkView(TournamentUtility.dpToPx(160))
            getBracketsFragment(position)!!.shrinkView(TournamentUtility.dpToPx(160))
        } else {
            val currentFragmentSize = getBracketsFragment(position + 1)!!.currentBracketSize
            val previousFragmentSize = getBracketsFragment(position + 1)!!.previousBracketSize
            if (currentFragmentSize != previousFragmentSize) {
                getBracketsFragment(position + 1)!!.expandHeight(TournamentUtility.dpToPx(320))
                getBracketsFragment(position)!!.shrinkView(TournamentUtility.dpToPx(160))
            }
        }

    }

    private fun initViews() {
        viewPager = view!!.findViewById<View>(R.id.container) as WrapContentHeightViewPager
    }

    // 스크롤 효과 나는동안 생기는것
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // Closer to current screen than to next
        if (position != mNextSelectedScreen) {
            mNextSelectedScreen = position
            //updateViewhere
            if (getBracketsFragment(position + 1)!!.currentBracketSize == getBracketsFragment(position + 1)!!.previousBracketSize) {
                getBracketsFragment(position + 1)!!.shrinkView(TournamentUtility.dpToPx(160))
                getBracketsFragment(position)!!.shrinkView(TournamentUtility.dpToPx(160))
            } else {
                val currentFragmentSize = getBracketsFragment(position + 1)!!.currentBracketSize
                val previousFragmentSize = getBracketsFragment(position + 1)!!.previousBracketSize
                if (currentFragmentSize != previousFragmentSize) {
                    getBracketsFragment(position + 1)!!.expandHeight(TournamentUtility.dpToPx(320))
                    getBracketsFragment(position)!!.shrinkView(TournamentUtility.dpToPx(160))
                }
            }
        }
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    fun getBracketsFragment(position: Int): BracketsColomnFragment? {
        var bracktsFrgmnt: BracketsColomnFragment? = null
        val fragments = childFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment is BracketsColomnFragment) {
                bracktsFrgmnt = fragment
                if (bracktsFrgmnt.sectionNumber == position)
                    break
            }
        }
        return bracktsFrgmnt
    }


    fun logB(x: Double, base: Double): Double {
        return Math.log(x) / Math.log(base)
    }

    private fun getUpload() {
        var isSuccess = true
        var bracktsFrgmnt: BracketsColomnFragment? = null
        val fragments = childFragmentManager.fragments

        for (fragment in fragments) {
            if (fragment is BracketsColomnFragment) {
                bracktsFrgmnt = fragment
            }
        }
        val totalItem = bracktsFrgmnt!!.getItem()
        for (item in totalItem!!) {
            for (matchItem in item.matches) {
                compositeDisposable.add(Api.postUpdateTournament(contestID, User().userID, "2", matchItem.competitorOne.round, matchItem.competitorOne.number.toInt(), contestDepartName,
                        matchItem.competitorOne.name, matchItem.competitorTwo.name, matchItem.competitorOne.score, matchItem.competitorTwo.score)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            if (!it.getSuccess())
                                isSuccess = false
                        }) {
                            isSuccess = false
                            Log.d("ERROR", it.message)
                        })
            }
        }
        if (isSuccess) {
            MaterialDialog(context!!).show {
                title(text = "토너먼트 대진표")
                message(text = "토너먼트 결과가 업데이트 되었습니다.")
                positiveButton(text = "확인") {
                    activity!!.finish()
                }
            }
        }
    }

    companion object {
        fun newInstance(): BracketsFragment {
            val args = Bundle()
            val fragment = BracketsFragment()
            fragment.arguments = args
            return fragment
        }
    }


}
