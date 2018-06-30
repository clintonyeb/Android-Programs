package com.example.clinton.light.dictionary_day;

import android.database.Cursor;
import android.util.Log;

import com.example.clinton.light.database.DictionaryContract;

public class WordDay {
    private String mWord;
    private String mSpeech;
    private String mDefinition;
    private String mRoot;
    private String mExample;

    public String getmDefinition () {
        return mDefinition;
    }

    public void setmDefinition (String mDefinition) {
        this.mDefinition = mDefinition;
    }



    public String getmRoot () {
        return mRoot;
    }

    public void setmRoot (String mRoot) {
        this.mRoot = mRoot;
    }

    public String getmExample () {
        return mExample;
    }

    public void setmExample (String mExample) {
        this.mExample = mExample;
    }

    public String getmSpeech () {
        return mSpeech;
    }

    public void setmSpeech (String mSpeech) {
        this.mSpeech = mSpeech;
    }

    public String getmWord () {
        return mWord;
    }

    public void setmWord (String mWord) {
        this.mWord = mWord;
    }

    public static WordDay ConvertCursorToObject(Cursor cursor)
    {
        WordDay wordDay = new WordDay();
        Log.i("OUTPUT", String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        wordDay.setmWord(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.WORD)));
        wordDay.setmDefinition(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.DEFINITION)));
        wordDay.setmRoot(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.ROOT)));
        wordDay.setmSpeech(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.SPEECH)));
        wordDay.setmExample(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.EXAMPLE)));
        return wordDay;
    }
}
