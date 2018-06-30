package com.example.clinton.companion.todayword;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.clinton.companion.activities.TodayWordActivity;
import com.example.clinton.companion.database.DictionaryContract;

public class RetrieveData extends AsyncTask<Object, Void, WordDay>
{
    ContentResolver dResolver;
    Context context;
    TodayWordActivity activity;

    @Override
    protected void onPostExecute (WordDay wordDay) {
        super.onPostExecute(wordDay);
        activity.DisplayData(wordDay);
    }

    @Override
    protected WordDay doInBackground (Object... params) {
        context = (Context)params[0];
        activity = (TodayWordActivity)params[1];
        dResolver = context.getContentResolver();
        return  QueryData();

    }

    private WordDay QueryData()
    {
        Cursor cursor = null;
        WordDay wordDay = null;
        try {
            cursor = dResolver.query(DictionaryContract.CONTENT_URI_TODAY, null, null, null, null, null);
            if ((cursor != null ? cursor.getCount() : 0) < 1) return null;
            wordDay = WordDay.ConvertCursorToObject(cursor);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return wordDay;
    }
}