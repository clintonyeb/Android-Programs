package com.example.clinton.companion.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DictionaryHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION  = 1;
    public static final String DATABASE_NAME = "dictionary.db";

    public DictionaryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate (SQLiteDatabase db) {
        DictionaryContract.DictionaryDataContract.onCreate(db);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        DictionaryContract.DictionaryDataContract.onUpgrade(db, oldVersion, newVersion);
    }
    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
