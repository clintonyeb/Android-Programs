package com.example.clinton.companion.dictionary.facades;


import android.database.Cursor;

import com.example.clinton.companion.database.DictionaryContract;

public class DictionaryFacade {
    private String mWord;
    private String mDefinition;
    private String mSpeech;
    private String mExamples;
    private long rowID;
    private long mFavorited;

    public static DictionaryFacade fromCursor (Cursor cursor)
    {
        DictionaryFacade facade = new DictionaryFacade();
        facade.setmWord(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.WORD)));
        facade.setmDefinition(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.DEFINITION_TEXT)));
        facade.setmSpeech(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.SPEECH)));
        facade.setmExamples(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.EXAMPLE_TEXT)));
        facade.setmFavorited(cursor.getLong(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.REC_FAVORITED)));
        facade.setRowID(cursor.getLong(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract._ID)));
        return facade;
    }

    public long getRowID() {
        return rowID;
    }

    public void setRowID(long rowID) {
        this.rowID = rowID;
    }

    public long getmFavorited() {
        return mFavorited;
    }

    public void setmFavorited(long mFavorited) {
        this.mFavorited = mFavorited;
    }

    public String getmDefinition() {
        return mDefinition;
    }

    public void setmDefinition(String mDefinition) {
        this.mDefinition = mDefinition;
    }

    public String getmExamples() {
        return mExamples;
    }

    public void setmExamples(String mExamples) {
        this.mExamples = mExamples;
    }

    public String getmSpeech() {
        return mSpeech;
    }

    public void setmSpeech(String mSpeech) {
        this.mSpeech = mSpeech;
    }

    public String getmWord() {
        return mWord;
    }

    public void setmWord(String mWord) {
        this.mWord = mWord;
    }

}

