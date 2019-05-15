package com.project.rankers.ui.contest.tournamentResult.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.project.rankers.data.model.db.ColomnData
import com.project.rankers.ui.contest.tournamentResult.Fragment.BracketsColomnFragment
import java.util.*


/**
 * Created by Emil on 21/10/17.
 */

class BracketsSectionAdapter(fm: FragmentManager, private val sectionList: ArrayList<ColomnData>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putSerializable("colomn_data", this.sectionList[position])
        bundle.putSerializable("data", this.sectionList)
        val fragment = BracketsColomnFragment()
        bundle.putInt("section_number", position)
        if (position > 0)
            bundle.putInt("previous_section_size", sectionList[position - 1].matches.size)
        else if (position == 0)
            bundle.putInt("previous_section_size", sectionList[position].matches.size)
        fragment.arguments = bundle

        return fragment
    }

    override fun getCount(): Int {
        return this.sectionList.size
    }
}
