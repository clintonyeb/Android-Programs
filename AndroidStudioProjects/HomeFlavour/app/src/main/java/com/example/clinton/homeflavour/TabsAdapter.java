package com.example.clinton.homeflavour;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int numTabs;
    TabsFragment newsFragment;

    public TabsAdapter(FragmentManager fm, int numbTabs, TabsFragment newsFragment) {
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
                return newsFragment.menu;
            case 1:
                return newsFragment.offer;
            default:
                throw new IllegalArgumentException();
        }

    }
}