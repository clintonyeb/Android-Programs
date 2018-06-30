package com.example.clinton.companion.tabsFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.clinton.companion.MainFragments.NewsFragment;


public class TabsAdapter extends FragmentStatePagerAdapter {
    int numTabs;
    NewsFragment newsFragment;

    public TabsAdapter(FragmentManager fm, int numbTabs, NewsFragment newsFragment) {
        super(fm);
        this.numTabs = numbTabs;
        this.newsFragment = newsFragment;
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return newsFragment.lifeStyleFrag;
            case 1:
                return newsFragment.scienceFrag;
            case 2:
                return newsFragment.sportFrag;
            case 3:
                return newsFragment.cultureFrag;
            case 4:
                return newsFragment.worldFrag;
            default:
                return null;
        }

    }
}