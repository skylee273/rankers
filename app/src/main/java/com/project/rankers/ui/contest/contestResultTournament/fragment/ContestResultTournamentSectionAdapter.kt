package com.project.rankers.ui.contest.contestResultTournament.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.project.rankers.data.model.db.ColomnData
import java.util.*

class ContestResultTournamentSectionAdapter(fm: FragmentManager, private val sectionList: ArrayList<ColomnData>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putSerializable("colomn_data", this.sectionList[position])
        val fragment = ContestResultTournamentColomnFragment()
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