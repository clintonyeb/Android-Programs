package com.example.holys.light.DATABASE_DIR;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by holys on 2/26/2016.
 */
public class NewsContract {
    public NewsContract(){}

    public static abstract class DataContract implements BaseColumns
    {
        public static final String TABLE_NAME = "newstable";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_THUMB_SOURCE = "thmbsource";
        public static final String COLUMN_NAME_WEBADDRESS = "webaddress";
        public static final String COLUMN_NAME_TAG = "tag";

        private static final String DATABASE_CREATE = "create table "+
                TABLE_NAME +
                "(" +
                _ID +
                " integer primary key autoincrement, " +
                COLUMN_NAME_DATE +
                " text not null, " +
                COLUMN_NAME_TITLE +
                " text not null, " +
                COLUMN_NAME_THUMB_SOURCE +
                " text not null, " +
                COLUMN_NAME_CONTENT +
                " text not null, " +
                COLUMN_NAME_TAG +
                " text not null, " +
                COLUMN_NAME_WEBADDRESS +
                " text not null);";

        public static void onCreate (SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        public static void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + NewsContract.DataContract.TABLE_NAME);
            onCreate(db);
        }

    }

}
