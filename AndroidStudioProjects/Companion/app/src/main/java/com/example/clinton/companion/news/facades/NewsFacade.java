package com.example.clinton.companion.news.facades;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.clinton.companion.database.NewsContract;

public class NewsFacade {


    private  String _Title;
    private  String _Date;
    private String _Text;
    private Bitmap _Thumbnail;
    private  String _WebAddress;
    private String _Tag;
    private String mImageUrl;
    private long mFavorited;
    private long rowID;

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
        Bitmap bitmap = null;
        if (imageLoc != null)
            bitmap = decodeBitmap(imageLoc);
        NewsFacade facade = new NewsFacade(title, date, content, bitmap, web, tag);
        facade.setRowID(cursor.getLong(cursor.getColumnIndex(NewsContract.DataContract._ID)));
        facade.setmFavorited(cursor.getLong(cursor.getColumnIndex(NewsContract.DataContract.COLUMN_FAVORITED)));
        return facade;
    }

    private static Bitmap decodeBitmap(byte[] bytes)
    {
       BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        options.inSampleSize = calculateInSampleSize(options, 130, 130);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public long getmFavorited() {
        return mFavorited;
    }

    public void setmFavorited(long mFavorited) {
        this.mFavorited = mFavorited;
    }

    public long getRowID() {
        return rowID;
    }

    public void setRowID(long rowID) {
        this.rowID = rowID;
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
