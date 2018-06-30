package com.example.holys.databasedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by holys on 2/15/2016.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {

    private final static String CREATE_ENTRIES = "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
            FeedReaderContract.FeedEntry._ID + "INTEGER PRIMARY KEY, " +
            FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " TEXT_TYPE, " +
            FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_TITLE + " TEXT_TYPE," +
            FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_SUBTITLE + "TEXT_TYPE )";

    private final static String DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
            FeedReaderContract.FeedEntry.TABLE_NAME;


    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FeedReader.db";
    public FeedReaderDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
