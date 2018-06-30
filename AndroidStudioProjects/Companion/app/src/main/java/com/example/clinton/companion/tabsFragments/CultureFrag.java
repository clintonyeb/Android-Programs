package com.example.clinton.companion.tabsFragments;

import android.os.Bundle;

import com.example.clinton.companion.utilities.FRAGMENT_ID;

public class CultureFrag extends AllTabsSuper {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        newsFeatures.setFragmentID(FRAGMENT_ID.CULTURE_ID);
        super.onActivityCreated(savedInstanceState);
    }
}
