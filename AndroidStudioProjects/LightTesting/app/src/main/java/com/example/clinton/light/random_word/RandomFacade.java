package com.example.clinton.light.random_word;


import android.database.Cursor;

import com.example.clinton.light.database.DictionaryContract;

public class RandomFacade {
    private String mWord;
    private String mDefinition;
    private String mSpeech;
    private String mExamples;

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

    private int wordID;
    private int mFavorited;

    public int getmFavorited() {
        return mFavorited;
    }

    public void setmFavorited(int mFavorited) {
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
    public static RandomFacade fromCursor (Cursor cursor)
    {
        RandomFacade facade = new RandomFacade();
        facade.setmWord(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.WORD)));
        facade.setmDefinition(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.DEFINITION)));
        facade.setmSpeech(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.SPEECH)));
        facade.setmExamples(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.EXAMPLE)));
        facade.setmFavorited(cursor.getInt(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.REC_FAVORITED)));
        facade.setWordID(cursor.getInt(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.WORD_ID)));
        return facade;
    }

}

