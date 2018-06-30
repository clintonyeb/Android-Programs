package com.example.clinton.companion.dictionaryFragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clinton.companion.R;
import com.example.clinton.companion.dictionary.facades.DictionaryHelper;
import com.example.clinton.companion.utilities.FRAGMENT_ID;


public class RecentFrag extends AllDictionaryFragment {

    public RecentFrag() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Fragment_ID = FRAGMENT_ID.DICTIONARY_RECENT_ID;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_dictionary, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), DictionaryHelper.GetDictionaryUri(Fragment_ID), null, null, null, "_id DESC");
    }




}
