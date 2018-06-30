package com.example.clinton.light.dictionary_main;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.clinton.light.activities.AllRecentActivity;
import com.example.clinton.light.database.DictionaryContract;

public class DeleteEntryTask extends AsyncTask<Object, Void, Void> {
    Context context;
    ContentResolver dResolver;
    @Override
    protected Void doInBackground (Object... params) {

        context = (Context)params[0];
        dResolver = context.getContentResolver();
        Delete((String)params[1]);
        return null;
    }
    private void Delete(String key)
    {
        Cursor cursor;
        long id;
        switch (AllRecentActivity.position)
        {
            case 0:

                cursor = dResolver.query(DictionaryContract.CONTENT_URI_RECENT, null,
                        DictionaryContract.DictionaryDataContract.WORD + "='" + key + "'", null,
                        null, null);
                if (cursor == null)
                    return;
                cursor.moveToFirst();
                id  =  cursor.getLong(cursor.getColumnIndex("_id"));
                dResolver.delete(DictionaryContract.CONTENT_URI_RECENT, "_id='"+id+"'" , null);
                cursor.close();
                break;
            case 1:
                cursor = dResolver.query(DictionaryContract.CONTENT_URI_FAVORITES, null,
                        DictionaryContract.DictionaryDataContract.WORD + "='" + key + "'", null,
                        null, null);
                if (cursor == null)
                    return;
                cursor.moveToFirst();
                id =  cursor.getLong(cursor.getColumnIndex("_id"));
                dResolver.delete(DictionaryContract.CONTENT_URI_FAVORITES, "_id='"+id+"'" , null);
                cursor.close();
                break;
        }

    }
}
