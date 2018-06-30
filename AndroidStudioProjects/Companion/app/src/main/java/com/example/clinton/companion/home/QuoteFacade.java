package com.example.clinton.companion.home;

import android.database.Cursor;

import com.example.clinton.companion.database.NewsContract;

public class QuoteFacade {
    String quoteText;
    String quoteAuthor;

    public static QuoteFacade fromCursor(Cursor cursor)
    {
        QuoteFacade quoteFacade = new QuoteFacade();
        quoteFacade.setQuoteText(cursor.getString(cursor.getColumnIndex(NewsContract.DataContract.QUOTE_TEXT)));
        quoteFacade.setQuoteAuthor(cursor.getString(cursor.getColumnIndex(NewsContract.DataContract.QUOTE_AUTHOR)));
        return quoteFacade;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }
}
