package com.example.clinton.companion.dictionary.workers;

import android.content.Context;
import android.os.AsyncTask;

import com.example.clinton.companion.utilities.FRAGMENT_ID;

import org.json.JSONObject;


public class HandleDataAsync extends AsyncTask<Object, Void, Void> {
    JSONObject JSONObject;
    Context context;

    @Override
    protected Void doInBackground(Object... params) {
        JSONObject = (JSONObject)params[0];
        context = (Context)params[1];
        FRAGMENT_ID fragmentId = (FRAGMENT_ID) params[2];
        ParseDictionaryData parseDictionaryData = new ParseDictionaryData();
        parseDictionaryData.ParseJSON(JSONObject, context, fragmentId);
        return null;
    }
}
