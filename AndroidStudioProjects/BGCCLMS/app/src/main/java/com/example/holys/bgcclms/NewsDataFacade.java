package com.example.holys.bgcclms;

/**
 * Created by holys on 2/17/2016.
 */

/**
 * Created by holys on 2/17/2016.
 */
public class NewsDataFacade {
    public String mNewsUri;
    public String mContent;
    public String mPubDate;
    public String mTitlte;

    public NewsDataFacade(String mNewsUri, String mContent, String mPubDate,  String mTitlte )
    {
        this.mNewsUri = mNewsUri;
        this.mContent = mContent;
        this.mPubDate = mPubDate;
        this.mTitlte = mTitlte;
    }
    public void setmNewsUri(String mNewsUri)
    {
        this.mNewsUri = mNewsUri;
    }

    public void setmContent(String mContent)
    {
        this.mContent = mContent;
    }
    public void setmPubDate(String mPubDate)
    {
        this.mPubDate = mPubDate;
    }
    public void setmTitlte(String mTitlte)
    {
        this.mTitlte = mTitlte;
    }

    public String getmNewsUri()
    {
        return mNewsUri;
    }

    public String getmContent()
    {
        return mContent;
    }
    public String getmPubDate()
    {
        return mPubDate;
    }

    public String getmTitlte()
    {
        return mTitlte;
    }

}

