package com.example.clinton.companion.home;


import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

public class HandleQuoteAsync extends AsyncTask<Object, Void, Void> {

    @Override
    protected Void doInBackground(Object... params) {
        PassQuote passQuote = new PassQuote();
        passQuote.doPassing((JSONObject)params[0], (Context)params[1]);
        return null;
    }
}
