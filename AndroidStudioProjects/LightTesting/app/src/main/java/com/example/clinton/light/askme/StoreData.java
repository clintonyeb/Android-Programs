package com.example.clinton.light.askme;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.clinton.light.R;
import com.example.clinton.light.database.NewsContract;
import com.example.clinton.light.newsdir.NewsFacade;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class StoreData extends AsyncTask<Object, Void, Void> {

    ContentResolver resolver;
    Context context;

    @Override
    protected Void doInBackground(Object... params) {
        context = (Context)params[0];
        resolver = context.getContentResolver();
        DeleteRows();
        InsertIntoTable((List<NewsFacade>)params[1]);
        return null;
    }
    private void DeleteRows()
    {
        resolver.delete(NewsContract.CONTENT_URI_SEARCH, null, null);
    }

    private void InsertIntoTable(List<NewsFacade> data) {
        for (NewsFacade facade :
                data) {
            ContentValues values = new ContentValues();
            values.put(NewsContract.DataContract.COLUMN_NAME_DATE, facade.getDate());
            values.put(NewsContract.DataContract.COLUMN_NAME_CONTENT, facade.getText());
            values.put(NewsContract.DataContract.COLUMN_NAME_TAG, facade.getTag());
            byte[] image = EncodeImage(facade.getThumb());
            values.put(NewsContract.DataContract.COLUMN_NAME_THUMB, image);
            values.put(NewsContract.DataContract.COLUMN_NAME_TITLE, facade.getTitle());
            values.put(NewsContract.DataContract.COLUMN_NAME_WEBADDRESS, facade.getWebAddress());
            Uri uri = resolver.insert(NewsContract.CONTENT_URI_SEARCH, values);
            Log.i("URI", " "  + uri);
        }
    }
    private byte[] EncodeImage(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (bitmap != null)
        {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        }
        else
        {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.door);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        }

        return stream.toByteArray();
    }
}
