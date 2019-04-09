package com.project.rankers.ui.tournament.adapter;

import android.os.Bundle;

import com.project.rankers.ui.tournament.Fragment.BracketsColomnFragment;
import com.project.rankers.ui.tournament.model.ColomnData;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by Emil on 21/10/17.
 */

public class BracketsSectionAdapter  extends FragmentStatePagerAdapter {

    private ArrayList<ColomnData> sectionList;


    public BracketsSectionAdapter(FragmentManager fm, ArrayList<ColomnData> sectionList) {
        super(fm);
        this.sectionList =sectionList;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("colomn_data", this.sectionList.get(position));
        BracketsColomnFragment fragment = new BracketsColomnFragment();
        bundle.putInt("section_number", position);
        if (position > 0)
            bundle.putInt("previous_section_size", sectionList.get(position - 1).getMatches().size());
        else if (position == 0)
            bundle.putInt("previous_section_size", sectionList.get(position).getMatches().size());
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return this.sectionList.size();
    }
}
