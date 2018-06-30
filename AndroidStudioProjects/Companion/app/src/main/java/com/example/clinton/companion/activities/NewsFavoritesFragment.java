package com.example.clinton.companion.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.clinton.companion.news.workers.GetUri;
import com.example.clinton.companion.tabsFragments.AllTabsSuper;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

public class NewsFavoritesFragment extends AllTabsSuper {

    public NewsFavoritesFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        newsFeatures.setFragmentID(FRAGMENT_ID.NEWS_FAVORITE_FRAG_ID);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void callToFetchData() {
        resetProgress();
    }

    public void CallToFetchData(String query)
    {
        resetProgress();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), GetUri.ThisURI(newsFeatures.getFragmentID()), null, null, null, "_id DESC");
    }
}
