package com.example.clinton.companion.home;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.clinton.companion.database.NewsContract;

public class StoreQuote {

    Context context;
    ContentResolver contentResolver;
    QuoteFacade facade;


    public StoreQuote(Context con, QuoteFacade fac)
    {
        context = con;
        facade = fac;
        contentResolver = context.getContentResolver();
    }

    public void doStoring()
    {
        deletePrevious();
        ContentValues values = new ContentValues();
        values.put(NewsContract.DataContract.QUOTE_TEXT, facade.getQuoteText());
        values.put(NewsContract.DataContract.QUOTE_AUTHOR, facade.getQuoteAuthor());
        Uri uri = contentResolver.insert(NewsContract.CONTENT_URI_QUOTE, values);
        Log.i("QUOTE", " " + uri);
    }
    private void deletePrevious()
    {
       contentResolver.delete(NewsContract.CONTENT_URI_QUOTE, null, null);
    }
}
