package com.example.clinton.light.database;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class DictionaryContract {
    public DictionaryContract(){}

    public static final int TODAY = 100;
    public static final int TODAY_ID = 115;
    public static final int FAVORITE = 120;
    public static final int FAVORITE_ID = 125;
    public static final int RECENT = 130;
    public static final int RECENT_ID = 135;
    public static final int OTHER = 140;
    public static final int OTHER_ID = 145;
    public static final int RANDOM = 150;
    public static final int RANDOM_ID = 155;
    public static final int DICTIONARY = 160;
    public static final int DICTIONARY_ID = 165;

    private static final String AUTHORITY = "com.example.clinton.light.provider.dictionary";
    public static final String TODAY_BASE_PATH = "today";
    public static final String FAVORITE_BASE_PATH = "favorites";
    public static final String RECENT_BASE_PATH = "recents";
    public static final String OTHER_BASE_PATH = "other";
    public static final String RANDOM_BASE_PATH = "random";
    public static final String DICTIONARY_BASE_PATH = "dictionary";

    public static final UriMatcher sURI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURI_MATCHER.addURI(AUTHORITY, TODAY_BASE_PATH, TODAY);
        sURI_MATCHER.addURI(AUTHORITY, TODAY_BASE_PATH + "/#", TODAY_ID);
        sURI_MATCHER.addURI(AUTHORITY, FAVORITE_BASE_PATH, FAVORITE);
        sURI_MATCHER.addURI(AUTHORITY, FAVORITE_BASE_PATH + "/#", FAVORITE_ID);
        sURI_MATCHER.addURI(AUTHORITY, RECENT_BASE_PATH, RECENT);
        sURI_MATCHER.addURI(AUTHORITY, RECENT_BASE_PATH + "/#", RECENT_ID);
        sURI_MATCHER.addURI(AUTHORITY, OTHER_BASE_PATH, OTHER);
        sURI_MATCHER.addURI(AUTHORITY, OTHER_BASE_PATH + "/#",  OTHER_ID);
        sURI_MATCHER.addURI(AUTHORITY, RANDOM_BASE_PATH, RANDOM);
        sURI_MATCHER.addURI(AUTHORITY, RANDOM_BASE_PATH + "/#",  RANDOM_ID);
        sURI_MATCHER.addURI(AUTHORITY, DICTIONARY_BASE_PATH, DICTIONARY);
        sURI_MATCHER.addURI(AUTHORITY, DICTIONARY_BASE_PATH + "/#",  DICTIONARY_ID);
    }

    public static final Uri CONTENT_URI_TODAY = Uri.parse("content://" + AUTHORITY + "/" + TODAY_BASE_PATH);
    public static final Uri CONTENT_URI_FAVORITES = Uri.parse("content://" + AUTHORITY + "/" + FAVORITE_BASE_PATH);
    public static final Uri CONTENT_URI_RECENT = Uri.parse("content://" + AUTHORITY + "/" + RECENT_BASE_PATH);
    public static final Uri CONTENT_URI_OTHER = Uri.parse("content://" + AUTHORITY + "/" + OTHER_BASE_PATH);
    public static final Uri CONTENT_URI_RANDOM = Uri.parse("content://" + AUTHORITY + "/" + RANDOM_BASE_PATH);
    public static final Uri CONTENT_URI_DICTIONARY = Uri.parse("content://" + AUTHORITY + "/" + DICTIONARY_BASE_PATH);

    public static abstract class DictionaryDataContract implements BaseColumns
    {
        public static final String TODAY_TABLE_NAME = "todaytable";
        public static final String FAVORITE_TABLE_NAME = "favoritetable";
        public static final String RECENT_TABLE_NAME = "recenttable";
        public static final String DICTIONARY_TABLE_NAME = "dictionarytable";
        public static final String OTHER_TABLE_NAME = "othertable";
        public static final String RANDOM_TABLE_NAME = "randomtable";

        public static final String WORD = "wordname";
        public static final String DEFINITION = "worddefinition";
        public static final String SPEECH = "wordspeech";
        public static final String EXAMPLE = "wordexample";
        public static final String REC_FAVORITED = "favorited";
        public static final String WORD_ID = "wordid";
        public static final String ROOT = "root";




        private static final String CREATE_FAVORITE_TABLE = CREATE_TABLE(FAVORITE_TABLE_NAME);
        private static final String CREATE_RECENT_TABLE = CREATE_TABLE(RECENT_TABLE_NAME);
        public static final String CREATE_DICTIONARY_TABLE = CREATE_TABLE(DICTIONARY_TABLE_NAME);
        public static final String CREATE_RANDOM_TABLE = CREATE_TABLE(RANDOM_TABLE_NAME);



        public static final String CREATE_TODAY_TABLE = "create table "+
                TODAY_TABLE_NAME +
                "(" +
                _ID +
                " integer primary key autoincrement, " +
                WORD +
                " text not null, " +
                DEFINITION +
                " text not null, "  +
                EXAMPLE +
                " text not null, " +
                SPEECH +
                " text not null, " +
                ROOT +
                " text not null);";

        public static final String CREATE_OTHER_TABLE = "create table "+
                OTHER_TABLE_NAME +
                "(" +
                _ID +
                " integer primary key autoincrement, " +
                WORD +
                " text not null);";

        public static void onCreate (SQLiteDatabase db) {
            db.execSQL(CREATE_TODAY_TABLE);
            db.execSQL(CREATE_FAVORITE_TABLE);
            db.execSQL(CREATE_DICTIONARY_TABLE);
            db.execSQL(CREATE_OTHER_TABLE);
            db.execSQL(CREATE_RANDOM_TABLE);
            db.execSQL(CREATE_RECENT_TABLE);

        }

        public static void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TODAY_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DICTIONARY_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + OTHER_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + RANDOM_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + RECENT_TABLE_NAME);
            onCreate(db);
        }
        private static String CREATE_TABLE(String TableName)
        {
           String str =  "create table "+
                    TableName +
                    "(" +
                    _ID +
                    " integer primary key autoincrement, " +
                    WORD +
                    " text not null, " +
                    DEFINITION +
                    " text not null, "  +
                    EXAMPLE +
                    " text not null, " +
                    SPEECH +
                    " text not null, " +
                    WORD_ID +
                    " int not null unique, "+
                    REC_FAVORITED +
                    " int not null);";
            return str;
        }

    }

}
