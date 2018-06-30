package com.example.clinton.companion.news.workers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.clinton.companion.database.DictionaryContract;
import com.example.clinton.companion.database.NewsContract;
import com.example.clinton.companion.news.facades.NewsFacade;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.FRAGMENT_ID;
import com.example.clinton.companion.utilities.HelperMethods;

public class DelFavNews extends AsyncTask<Object, Void, Void> {

    Context context;
    ContentResolver contentResolver;
    FRAGMENT_ID fragmentId;
    NewsFacade facade;

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        HelperMethods.sendBroadCast(NewsResults.RemovedFromFavorites, context);
    }

    @Override
    protected Void doInBackground(Object... params) {
        context = (Context)params[1];
        fragmentId = (FRAGMENT_ID)params[2];
        facade = (NewsFacade) params[3];
        contentResolver = context.getContentResolver();
        RemoveFromFav();
        UpdateCurrentData();
        return null;
    }

    private void UpdateCurrentData() {
        long stored_id = facade.getRowID();
        String filter = DictionaryContract.DictionaryDataContract._ID + "=" + stored_id;
        ContentValues args = new ContentValues();
        args.put(NewsContract.DataContract.COLUMN_FAVORITED, 0);
        int i = contentResolver.update(GetUri.ThisURI(fragmentId), args, filter, null);
        Log.i("UPDATED", " " + i);
    }

    private void RemoveFromFav() {
        long stored_id = facade.getmFavorited();
        String filter = NewsContract.DataContract._ID + "=" + stored_id;
        int i = contentResolver.delete(NewsContract.CONTENT_URI_FAVORITE, filter, null);
        Log.i("DELETED", " " + i);
    }
}
