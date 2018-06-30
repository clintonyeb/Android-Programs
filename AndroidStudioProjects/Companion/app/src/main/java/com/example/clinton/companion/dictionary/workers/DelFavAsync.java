package com.example.clinton.companion.dictionary.workers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.clinton.companion.database.DictionaryContract;
import com.example.clinton.companion.dictionary.facades.DictionaryFacade;
import com.example.clinton.companion.dictionary.facades.DictionaryHelper;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.FRAGMENT_ID;
import com.example.clinton.companion.utilities.HelperMethods;

public class DelFavAsync extends AsyncTask<Object, Void, Void>  {

    Context context;
    ContentResolver contentResolver;
    FRAGMENT_ID fragmentId;
    DictionaryFacade facade;

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        HelperMethods.sendBroadCast(NewsResults.RemovedFromFavorites, context);
    }

    @Override
    protected Void doInBackground(Object... params) {
        context = (Context)params[1];
        fragmentId = (FRAGMENT_ID)params[2];
        facade = (DictionaryFacade)params[3];
        contentResolver = context.getContentResolver();
        RemoveFromFav();
        UpdateCurrentData();
        return null;
    }

    private void UpdateCurrentData() {
        long stored_id = facade.getRowID();
        String filter = DictionaryContract.DictionaryDataContract._ID + "=" + stored_id;
        ContentValues args = new ContentValues();
        args.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, 0);
        int i = contentResolver.update(DictionaryHelper.GetDictionaryUri(fragmentId), args, filter, null);
        Log.i("UPDATED", " " + i);
    }

    private void RemoveFromFav() {
        long stored_id = facade.getmFavorited();
        String filter = DictionaryContract.DictionaryDataContract._ID + "=" + stored_id;
        contentResolver.delete(DictionaryContract.CONTENT_URI_FAVORITES, filter, null );
    }
}
