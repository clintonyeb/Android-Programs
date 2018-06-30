package com.example.clinton.companion.dictionary.facades;


import android.database.Cursor;

import com.example.clinton.companion.database.DictionaryContract;

public class RelatedFacade {
    private  String word;

    public static RelatedFacade fromCursor(Cursor cursor)
    {
        RelatedFacade facade = new RelatedFacade();
        facade.setWord(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.WORD)));
        return facade;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
