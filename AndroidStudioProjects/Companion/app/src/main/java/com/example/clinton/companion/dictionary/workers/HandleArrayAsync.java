package com.example.clinton.companion.dictionary.workers;

import android.content.Context;
import android.os.AsyncTask;

import com.example.clinton.companion.utilities.FRAGMENT_ID;

import org.json.JSONArray;

public class HandleArrayAsync extends AsyncTask<Object, Void, Void> {
    org.json.JSONArray JSONArray;
    Context context;

    @Override
    protected Void doInBackground(Object... params) {
        JSONArray = (JSONArray)params[0];
        context = (Context)params[1];
        FRAGMENT_ID fragmentId = (FRAGMENT_ID) params[2];
        ParseRelativeWord parseRelativeWord = new ParseRelativeWord();
        parseRelativeWord.ParseJSON(JSONArray, context);
        return null;
    }
}
