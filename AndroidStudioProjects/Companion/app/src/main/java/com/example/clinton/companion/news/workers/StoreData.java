package com.example.clinton.companion.news.workers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.clinton.companion.database.NewsContract;
import com.example.clinton.companion.news.facades.NewsFacade;
import com.example.clinton.companion.news.workers.GetUri;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

import java.io.ByteArrayOutputStream;

public class StoreData {
    Context context;
    ContentResolver contentResolver;
    NewsFacade facade;
    FRAGMENT_ID NewsID;

    public StoreData(NewsFacade fac, Context con, FRAGMENT_ID newsID)
    {
        facade = fac;
        context = con;
        NewsID = newsID;
        contentResolver = context.getContentResolver();
    }


    public void StoringData()
    {
        ContentValues values = new ContentValues();
        values.put(NewsContract.DataContract.COLUMN_NAME_DATE, facade.getDate());
        values.put(NewsContract.DataContract.COLUMN_NAME_CONTENT, facade.getText());
        values.put(NewsContract.DataContract.COLUMN_NAME_TAG, facade.getTag());
        byte[] image = EncodeImage(facade.getThumb());
        values.put(NewsContract.DataContract.COLUMN_NAME_THUMB, image);
        values.put(NewsContract.DataContract.COLUMN_NAME_TITLE, facade.getTitle());
        values.put(NewsContract.DataContract.COLUMN_NAME_WEBADDRESS, facade.getWebAddress());
        values.put(NewsContract.DataContract.COLUMN_FAVORITED, 0);
        Uri uri = contentResolver.insert(GetUri.ThisURI(NewsID), values);
        Log.i("URI", " " + uri);
    }
    private byte[] EncodeImage(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        }catch (Exception e)
        {
           return null;
        }

        return stream.toByteArray();
    }
    public void DeleteData()
    {
        contentResolver.delete(GetUri.ThisURI(NewsID), null, null);
    }

}
