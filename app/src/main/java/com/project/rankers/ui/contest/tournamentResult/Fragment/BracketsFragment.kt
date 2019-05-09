package com.project.rankers.ui.contest.tournamentResult.Fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.project.rankers.R
import com.project.rankers.data.model.db.ColomnData
import com.project.rankers.data.model.db.CompetitorData
import com.project.rankers.data.model.db.MatchData
import com.project.rankers.data.remote.api.Api
import com.project.rankers.ui.contest.tournamentResult.adapter.BracketsSectionAdapter
import com.project.rankers.ui.contest.tournamentResult.customviews.WrapContentHeightViewPager
import com.project.rankers.ui.contest.tournamentResult.utility.TournamentUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Created by Emil on 21/10/17.
 */

class BracketsFragment : Fragment(), ViewPager.OnPageChangeListener {

    private var viewPager: WrapContentHeightViewPager? = null
    private var sectionAdapter: BracketsSectionAdapter? = null
    private var sectionList: ArrayList<ColomnData>? = null
    private var mNextSelectedScreen: Int = 0
    private val mCurrentPagerState: Int = 0
    lateinit var compositeDisposable: CompositeDisposable
    private var tournamentPeopleList: ArrayList<String>? = null
    private var contestID: String = ""
    private var contestDepartName: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (arguments != null) {
            contestID = arguments!!.getString("CONTEST_ID") // 전달한 key 값
            contestDepartName = arguments!!.getString("CONTEST_DEPART") // 전달한 key 값
        }
        compositeDisposable = CompositeDisposable()

        return inflater.inflate(R.layout.fragment_brackts, container, false)
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
        tournamentPeopleList = ArrayList()
        compositeDisposable.add(Api.getTournamentList(contestID, contestDepartName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    for (item in response.items) {
                        tournamentPeopleList!!.add(item.tournamentPlayerOne!!)
                        tournamentPeopleList!!.add(item.tournamentPlayerTwo!!)
                    }
                    setData()
                    intialiseViewPagerAdapter()
                }) {
                    Log.e("Error",it.toString())
                })
    }
    private fun setData() {
        sectionList = ArrayList()
        val nextPow = nextPowerOf2(tournamentPeopleList!!.size) //32
        val tournamentSize = logB(nextPow.toDouble(), 2.0) // 5
        //27
        val columnDataList = arrayOfNulls<ArrayList<MatchData>>(tournamentSize.toInt()) // 5
        val rowList = arrayOfNulls<CompetitorData>(nextPow) // 32
        val matchDataList = arrayOfNulls<MatchData>(rowList.size / 2) // 16

        var mod = 1
        for (i in 0 until columnDataList.size) { // 0 ~ 4
            columnDataList[i] = ArrayList<MatchData>()
            for (j in 0 until (rowList.size / mod)) {     // 0 ~ 31
                if(mod == 1){
                    if(j < tournamentPeopleList!!.size){
                        rowList[j] = CompetitorData(tournamentPeopleList!![j], "0", (rowList.size / mod).toString() + "강", "2019.04.10") // 28 개 넣음
                    }else{
                        rowList[j] = CompetitorData("", "", (rowList.size / mod).toString() + "강", "2019.04.10") // 28 개 넣음
                    }
                }else{
                    rowList[j] = CompetitorData("경기전", "", (rowList.size / mod).toString() + "강", "2019.04.10") // 28 개 넣음
                }
            }
            for (k in 0 until (matchDataList.size / mod)) {   // 0 ~ 16
                val z = (k * 2)
                matchDataList[k] = MatchData(rowList[z], rowList[z + 1]) // 14개 넣음
                columnDataList[i]!!.add(matchDataList[k]!!)
            }
            mod *= 2
            sectionList!!.add(ColomnData(columnDataList[i]))
        }
    }

    private fun intialiseViewPagerAdapter() {

        sectionAdapter = BracketsSectionAdapter(childFragmentManager, this.sectionList)
        viewPager!!.offscreenPageLimit = 10
        viewPager!!.adapter = sectionAdapter
        viewPager!!.currentItem = 0
        viewPager!!.pageMargin = -200
        viewPager!!.isHorizontalFadingEdgeEnabled = true
        viewPager!!.setFadingEdgeLength(50)

        viewPager!!.addOnPageChangeListener(this)
    }

    private fun initViews() {
        viewPager = view!!.findViewById<View>(R.id.container) as WrapContentHeightViewPager
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        if (mCurrentPagerState != ViewPager.SCROLL_STATE_SETTLING) {
            // We are moving to next screen on right side
            if (positionOffset > 0.5) {
                // Closer to next screen than to current
                if (position + 1 != mNextSelectedScreen) {
                    mNextSelectedScreen = position + 1
                    //update view here
                    if (getBracketsFragment(position)!!.colomnList[0].height != TournamentUtility.dpToPx(131))
                        getBracketsFragment(position)!!.shrinkView(TournamentUtility.dpToPx(131))
                    if (getBracketsFragment(position + 1)!!.colomnList[0].height != TournamentUtility.dpToPx(131))
                        getBracketsFragment(position + 1)!!.shrinkView(TournamentUtility.dpToPx(131))
                }
            } else {
                // Closer to current screen than to next
                if (position != mNextSelectedScreen) {
                    mNextSelectedScreen = position
                    //updateViewhere

                    if (getBracketsFragment(position + 1)!!.currentBracketSize == getBracketsFragment(position + 1)!!.previousBracketSize) {
                        getBracketsFragment(position + 1)!!.shrinkView(TournamentUtility.dpToPx(131))
                        getBracketsFragment(position)!!.shrinkView(TournamentUtility.dpToPx(131))
                    } else {
                        val currentFragmentSize = getBracketsFragment(position + 1)!!.currentBracketSize
                        val previousFragmentSize = getBracketsFragment(position + 1)!!.previousBracketSize
                        if (currentFragmentSize != previousFragmentSize) {
                            getBracketsFragment(position + 1)!!.expandHeight(TournamentUtility.dpToPx(262))
                            getBracketsFragment(position)!!.shrinkView(TournamentUtility.dpToPx(131))
                        }
                    }
                }
            }
        } else {
            // We are moving to next screen left side
            if (positionOffset > 0.5) {
                // Closer to current screen than to next
                if (position + 1 != mNextSelectedScreen) {
                    mNextSelectedScreen = position + 1
                    //update view for screen

                }
            } else {
                // Closer to next screen than to current
                if (position != mNextSelectedScreen) {
                    mNextSelectedScreen = position
                    //updateviewfor screem
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
        if (childFragmentManager != null) {
            val fragments = childFragmentManager.fragments
            if (fragments != null) {
                for (fragment in fragments) {
                    if (fragment is BracketsColomnFragment) {
                        bracktsFrgmnt = fragment
                        if (bracktsFrgmnt.sectionNumber == position)
                            break
                    }
                }
            }
        }
        return bracktsFrgmnt
    }

    fun nextPowerOf2(number: Int): Int {
        var count = 0
        var n = number
        if (n > 0 && n and n - 1 == 0)
            return n

        while (n != 0) {
            n = n shr 1
            count += 1
        }

        return 1 shl count
    }

    fun logB(x: Double, base: Double): Double {
        return Math.log(x) / Math.log(base)
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
