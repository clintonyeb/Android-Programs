package com.example.clinton.companion.dictionary.workers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.clinton.companion.database.DictionaryContract;
import com.example.clinton.companion.dictionary.facades.DictionaryHelper;
import com.example.clinton.companion.dictionary.facades.DictionaryFacade;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.FRAGMENT_ID;
import com.example.clinton.companion.utilities.HelperMethods;

public class HandleFavWordAsync extends AsyncTask<Object, Void, Void> {

    long id;
    Context context;
    ContentResolver contentResolver;
    FRAGMENT_ID fragmentId;
    DictionaryFacade facade;

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        HelperMethods.sendBroadCast(NewsResults.AddedToFavorites, context);
    }

    @Override
    protected Void doInBackground(Object... params) {
        id = (long)params[0];
        context = (Context)params[1];
        fragmentId = (FRAGMENT_ID)params[2];
        facade = (DictionaryFacade)params[3];
        contentResolver = context.getContentResolver();
        long stored_id = StoreInFavTable();
        UpdateCurrentData(stored_id);
        return null;
    }

    private void UpdateCurrentData(long stored_id)
    {
        String filter = DictionaryContract.DictionaryDataContract._ID + "=" + id;
        ContentValues args = new ContentValues();
        args.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, stored_id);
        int i = contentResolver.update(DictionaryHelper.GetDictionaryUri(fragmentId), args, filter, null);
        Log.i("UPDATE", " " + i);
    }

    private long StoreInFavTable()
    {
        long id = -1;
        ContentValues values = new ContentValues();
        values.put(DictionaryContract.DictionaryDataContract.WORD, facade.getmWord());
        values.put(DictionaryContract.DictionaryDataContract.DEFINITION_TEXT, facade.getmDefinition());
        values.put(DictionaryContract.DictionaryDataContract.EXAMPLE_TEXT, facade.getmExamples());
        values.put(DictionaryContract.DictionaryDataContract.SPEECH, facade.getmSpeech());
        values.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, 1);
        Uri uri = contentResolver.insert(DictionaryContract.CONTENT_URI_FAVORITES, values);
        if (uri != null) {
            id = Long.valueOf(uri.getLastPathSegment());
        }
        return id;
    }
}
