package com.example.clinton.companion.tabsFragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;

import com.example.clinton.companion.utilities.FRAGMENT_ID;

public class SearchFrag extends AllTabsSuper {

    private OnSearchFragmentInteractionListener mListener;

    public SearchFrag() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        newsFeatures.setFragmentID(FRAGMENT_ID.NEWS_SEARCH_FRAGMENT_ID);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchFragmentInteractionListener) {
            mListener = (OnSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       super.onLoadFinished(loader, data);
        if (mListener != null)
        {
            mListener.onFragmentInteraction(this);
        }
    }

    public interface OnSearchFragmentInteractionListener {
        void onFragmentInteraction(SearchFrag searchFrag);
    }


}
