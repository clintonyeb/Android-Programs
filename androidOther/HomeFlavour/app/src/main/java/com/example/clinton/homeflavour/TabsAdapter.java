package com.example.clinton.homeflavour;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabsAdapter extends FragmentStatePagerAdapter {
    int numTabs;
    AllTabs tabs;

    public TabsAdapter(FragmentManager fm, int numbTabs, AllTabs tabs) {
        super(fm);
        this.numTabs = numbTabs;
        this.tabs = tabs;
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new HomeFragment();
            case 3:
                return new HomeFragment();
            case 4:
                return new HomeFragment();
            case 5:
                return new HomeFragment();
            default:
                throw new IllegalArgumentException();
        }

    }
}