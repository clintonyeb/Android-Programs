package com.example.clinton.light.dict_other;


import android.database.Cursor;

import com.example.clinton.light.database.DictionaryContract;

public class DictOtherFacade {
    private String mWord;

    public String getmWord () {
        return mWord;
    }

    public void setmWord (String mWord) {
        this.mWord = mWord;
    }
    public static DictOtherFacade fromCursor(Cursor cursor)
    {
        DictOtherFacade facade = new DictOtherFacade();
        facade.setmWord(cursor.getString(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.WORD)));
        return facade;
    }

}
