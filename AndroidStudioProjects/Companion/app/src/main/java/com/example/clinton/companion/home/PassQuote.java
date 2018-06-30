package com.example.clinton.companion.home;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class PassQuote {

    public void doPassing(JSONObject JSON, Context c)
    {
        QuoteFacade quoteFacade = new QuoteFacade();
        try {
            quoteFacade.setQuoteText(JSON.getString("quoteText"));
            quoteFacade.setQuoteAuthor("#" + JSON.getString("quoteAuthor"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StoreQuote storeQuote = new StoreQuote(c, quoteFacade);
        storeQuote.doStoring();
    }
}
