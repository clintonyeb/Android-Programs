package com.example.clinton.companion.todayword;

import android.database.Cursor;
import android.util.Log;

import com.example.clinton.companion.database.DictionaryContract;

public class WordDay {
    private String mWord;
    private Definitions mDefinition;
    private String mRoot;
    private Examples mExample;

    public static WordDay ConvertCursorToObject(Cursor cursor)
    {
        WordDay wordDay = new WordDay();
        Log.i("OUTPUT", String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        wordDay.setmWord(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.WORD)));
        Definitions definitions = Definitions.newInstance();
        definitions.setPartOfSpeech(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.DEFINITION_SPEECH)));
        definitions.setText(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.DEFINITION_TEXT)));
        wordDay.setmDefinition(definitions);
        Examples examples = Examples.newInstance();
        examples.setText(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.EXAMPLE_TEXT)));
        examples.setTitle(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.EXAMPLE_TITLE)));
        wordDay.setmExample(examples);
        wordDay.setmRoot(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.ROOT)));
        return wordDay;
    }

    public Definitions getmDefinition() {
        return mDefinition;
    }

    public void setmDefinition(Definitions mDefinition) {
        this.mDefinition = mDefinition;
    }

    public Examples getmExample() {
        return mExample;
    }

    public void setmExample(Examples mExample) {
        this.mExample = mExample;
    }

    public String getmRoot () {
        return mRoot;
    }

    public void setmRoot (String mRoot) {
        this.mRoot = mRoot;
    }

    public String getmWord () {
        return mWord;
    }

    public void setmWord (String mWord) {
        this.mWord = mWord;
    }
}

