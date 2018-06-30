package com.example.clinton.light.newsdir;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.clinton.light.database.NewsContract;

public class NewsFacade {
    private String _Title;
    private String _Date;
    private String _Text;
    private Bitmap _Thumbnail;
    private String _WebAddress;
    private String _Tag;
    private String mImageUrl;

    public NewsFacade(String title, String date, String text, Bitmap thum, String web, String tag)
    {
        _Title = title;
        _Date = date;
        _Text = text;
        _Thumbnail = thum;
        _WebAddress = web;
        _Tag = tag;
        mImageUrl = null;
    }

    public NewsFacade() {}

    public static NewsFacade fromCursor(Cursor cursor)
    {
        String title = cursor.getString(cursor.getColumnIndexOrThrow(NewsContract.DataContract.COLUMN_NAME_TITLE));
        String date = cursor.getString(cursor.getColumnIndex(NewsContract.DataContract.COLUMN_NAME_DATE));
        String tag = cursor.getString(cursor.getColumnIndex(NewsContract.DataContract.COLUMN_NAME_TAG));
        String content = cursor.getString(cursor.getColumnIndex(NewsContract.DataContract.COLUMN_NAME_CONTENT));
        byte[] imageLoc = cursor.getBlob(cursor.getColumnIndex(NewsContract.DataContract.COLUMN_NAME_THUMB));
        String web = cursor.getString(cursor.getColumnIndex(NewsContract.DataContract.COLUMN_NAME_WEBADDRESS));
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageLoc, 0, imageLoc.length);
        return new NewsFacade(title, date, content, bitmap, web, tag);
    }

    public String getTitle()
    {
        return this._Title;
    }

    public String getDate()
    {
        return this._Date;
    }
    public String getText()
    {
        return this._Text;
    }
    public String getImageUrl()
    {
        return this.mImageUrl;
    }

    public Bitmap getThumb()
    {
        return this._Thumbnail;
    }

    public void setThumb(Bitmap bitmap) {
        this._Thumbnail = bitmap;
    }

    public String getWebAddress ()
    {
        return this._WebAddress;
    }

    public String getTag()
    {
        return this._Tag;
    }

}
