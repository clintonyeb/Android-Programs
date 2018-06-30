package com.example.clinton.companion.news.workers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.clinton.companion.database.NewsContract;
import com.example.clinton.companion.news.facades.NewsFacade;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.FRAGMENT_ID;
import com.example.clinton.companion.utilities.HelperMethods;

import java.io.ByteArrayOutputStream;


public class HandleFavoriteAsync extends AsyncTask<Object, Void, Void> {
    long id;
    Context context;
    ContentResolver contentResolver;
    FRAGMENT_ID fragmentId;
    NewsFacade facade;

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
        facade = (NewsFacade)params[3];
        contentResolver = context.getContentResolver();
        long stored_id = StoreInFavTable();
        UpdateCurrentData(stored_id);
        return null;
    }

    private void UpdateCurrentData(long stored_id)
    {
        String filter = NewsContract.DataContract._ID + "=" + id;
        ContentValues args = new ContentValues();
        args.put(NewsContract.DataContract.COLUMN_FAVORITED, stored_id);
        int uri = contentResolver.update(GetUri.ThisURI(fragmentId), args, filter, null);
        Log.i("UPDATED", " " + " "  +uri);
    }

    private long StoreInFavTable()
    {
        long id = -1;
        ContentValues values = new ContentValues();
        values.put(NewsContract.DataContract.COLUMN_NAME_DATE, facade.getDate());
        values.put(NewsContract.DataContract.COLUMN_NAME_CONTENT, facade.getText());
        values.put(NewsContract.DataContract.COLUMN_NAME_TAG, facade.getTag());
        byte[] image = EncodeImage(facade.getThumb());
        values.put(NewsContract.DataContract.COLUMN_NAME_THUMB, image);
        values.put(NewsContract.DataContract.COLUMN_NAME_TITLE, facade.getTitle());
        values.put(NewsContract.DataContract.COLUMN_NAME_WEBADDRESS, facade.getWebAddress());
        values.put(NewsContract.DataContract.COLUMN_FAVORITED, 1);
        Uri uri = contentResolver.insert(NewsContract.CONTENT_URI_FAVORITE, values);
        if (uri != null) {
            id = Long.valueOf(uri.getLastPathSegment());
        }
        return id;
    }

    private byte[] EncodeImage(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        return stream.toByteArray();
    }
}
