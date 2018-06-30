package com.example.holys.light.NEWS_DIR;

import android.graphics.Bitmap;

/**
 * Created by holys on 2/25/2016.
 */
public class NewsFacade {
    private String _Title;
    private String _Date;
    private String _Text;
    private Bitmap _Thumbnail;
    private String _WebAddress;
    private String _Tag;

    public NewsFacade(String title, String date, String text, Bitmap thum, String web, String tag)
    {
        _Title = title;
        _Date = date;
        _Text = text;
        _Thumbnail = thum;
        _WebAddress = web;
        _Tag = tag;
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
    public Bitmap getThumb()
    {
        return this._Thumbnail;
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
