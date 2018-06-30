package com.example.clinton.companion.tabsFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.clinton.companion.MainFragments.NewsSearchFragment;


public class SearchAdapter extends FragmentStatePagerAdapter {
    int numTabs;
    NewsSearchFragment fragment;


    public SearchAdapter(FragmentManager fm, int numbTabs, NewsSearchFragment fragment) {
        super(fm);
        this.numTabs = numbTabs;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment.currentFragment = new SearchFrag();
                return fragment.currentFragment;
            default:
                return null;
        }

    }
}