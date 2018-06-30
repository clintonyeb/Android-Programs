package com.example.holys.light;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by holys on 2/23/2016.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {
    public TabsAdapter (FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount () {
        return 3;
    }

    @Override
    public Fragment getItem (int position) {
        switch (position)
        {
            case 0:
                return new TabsFragment();
            case 1:
                return new Tabs2();
            case 2:
                return new Tabs3();
            default:
                return null;
        }

    }
}
