package com.example.clinton.light.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by clinton on 2/23/2016.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {
    int numTabs;

    public TabsAdapter(FragmentManager fm, int numbTabs) {
        super(fm);
        this.numTabs = numbTabs;
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LifeStyleFrag();
            case 1:
                return new ScienceFrag();
            case 2:
                return new SportFrag();
            case 3:
                return new CultureFrag();
            case 4:
                return new WorldFrag();
            default:
                return null;
        }

    }
}
