package com.example.clinton.light.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by clinton on 2/26/2016.
 */
public class NewsDataHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION  = 2;
    public static final String DATABASE_NAME = "newsdatabase.db";

    public NewsDataHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        NewsContract.DataContract.onCreate(db);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        NewsContract.DataContract.onUpgrade(db, oldVersion, newVersion);
    }
    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
