package com.example.clinton.companion.database;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;


public class NewsContract {
    public static final int WORLD = 10;
    public static final int WORLD_ID = 15;
    public static final int SCIENCE = 20;
    public static final int SCIENCE_ID = 25;
    public static final int SPORT = 30;
    public static final int SPORT_ID = 35;
    public static final int CULTURE = 40;
    public static final int CULTURE_ID = 45;
    public static final int LIFESTYLE = 50;
    public static final int LIFESTYLE_ID = 55;
    public static final int SEARCH = 60;
    public static final int SEARCH_ID = 65;
    public static final int FAVORITE = 70;
    public static final int FAVORITE_ID = 75;
    public static final int QUOTE = 80;
    public static final int QUOTE_ID = 85;
    public static final String WORLD_BASE_PATH = "world";
    public static final String SCIENCE_BASE_PATH = "science";
    public static final String SPORT_BASE_PATH = "sport";
    public static final String CULTURE_BASE_PATH=  "culture";
    public static final String LIFESTYLE_BASE_PATH = "lifestyle";
    public static final String SEARCH_BASE_PATH = "search";
    public static final String FAVORITE_BASE_PATH = "favorite";
    public static final String QUOTE_BASE_PATH = "quote";

    public static final UriMatcher sURI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "com.example.clinton.companion.provider";
    public static final Uri CONTENT_URI_WORLD = Uri.parse("content://" + AUTHORITY + "/" + WORLD_BASE_PATH);
    public static final Uri CONTENT_URI_SCIENCE = Uri.parse("content://" + AUTHORITY + "/" + SCIENCE_BASE_PATH);
    public static final Uri CONTENT_URI_SPORT = Uri.parse("content://" + AUTHORITY + "/" + SPORT_BASE_PATH);
    public static final Uri CONTENT_URI_CULTURE = Uri.parse("content://" + AUTHORITY + "/" + CULTURE_BASE_PATH);
    public static final Uri CONTENT_URI_LIFESTYLE = Uri.parse("content://" + AUTHORITY + "/" + LIFESTYLE_BASE_PATH);
    public static final Uri CONTENT_URI_SEARCH = Uri.parse("content://" + AUTHORITY + "/" + SEARCH_BASE_PATH);
    public static final Uri CONTENT_URI_FAVORITE = Uri.parse("content://" + AUTHORITY + "/" + FAVORITE_BASE_PATH);
    public static final Uri CONTENT_URI_QUOTE = Uri.parse("content://" + AUTHORITY + "/" + QUOTE_BASE_PATH);

    static {
        sURI_MATCHER.addURI(AUTHORITY, WORLD_BASE_PATH, WORLD);
        sURI_MATCHER.addURI(AUTHORITY, WORLD_BASE_PATH + "/#", WORLD_ID);
        sURI_MATCHER.addURI(AUTHORITY, SCIENCE_BASE_PATH, SCIENCE);
        sURI_MATCHER.addURI(AUTHORITY, SCIENCE_BASE_PATH + "/#", SCIENCE_ID);
        sURI_MATCHER.addURI(AUTHORITY, SPORT_BASE_PATH, SPORT);
        sURI_MATCHER.addURI(AUTHORITY, SPORT_BASE_PATH + "/#", SPORT_ID);
        sURI_MATCHER.addURI(AUTHORITY, CULTURE_BASE_PATH, CULTURE);
        sURI_MATCHER.addURI(AUTHORITY, CULTURE_BASE_PATH + "/#", CULTURE_ID);
        sURI_MATCHER.addURI(AUTHORITY, LIFESTYLE_BASE_PATH, LIFESTYLE);
        sURI_MATCHER.addURI(AUTHORITY, LIFESTYLE_BASE_PATH + "/#", LIFESTYLE_ID);
        sURI_MATCHER.addURI(AUTHORITY, SEARCH_BASE_PATH, SEARCH);
        sURI_MATCHER.addURI(AUTHORITY, SEARCH_BASE_PATH + "/#", SEARCH_ID);
        sURI_MATCHER.addURI(AUTHORITY, FAVORITE_BASE_PATH, FAVORITE);
        sURI_MATCHER.addURI(AUTHORITY, FAVORITE_BASE_PATH + "/#", FAVORITE_ID);
        sURI_MATCHER.addURI(AUTHORITY, QUOTE_BASE_PATH, QUOTE);
        sURI_MATCHER.addURI(AUTHORITY,  QUOTE_BASE_PATH + "/#",QUOTE_ID);
    }

    public NewsContract() {
    }

    public static abstract class DataContract implements BaseColumns
    {
        public static final String WORLD_TABLE_NAME = "worldtable";
        public static final String SCIENCE_TABLE_NAME = "sciencetable";
        public static final String SPORT_TABLE_NAME = "sporttable";
        public static final String CULTURE_TABLE_NAME = "culturetable";
        public static final String LIFESTYLE_TABLE_NAME = "lifestyletable";
        public static final String SEARCH_TABLE_NAME = "searchtable";
        public static final String FAVORITE_TABLE_NAME = "favoritetable";
        public static final String QUOTE_TABLE_NAME = "quotetable";


        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_THUMB = "imagedata";
        public static final String COLUMN_NAME_WEBADDRESS = "webaddress";
        public static final String COLUMN_NAME_TAG = "tag";
        public static final String COLUMN_FAVORITED  = "favorite";
        public static final String QUOTE_TEXT = "text";
        public static final String QUOTE_AUTHOR = "author";

        public static final String DATABASE_CREATE_WORLD = CREATE_TABLES(WORLD_TABLE_NAME);
        public static final String DATABASE_CREATE_SCIENCE = CREATE_TABLES(SCIENCE_TABLE_NAME);
        public static final String DATABASE_CREATE_SPORT = CREATE_TABLES(SPORT_TABLE_NAME);
        public static final String DATABASE_CREATE_CULTURE = CREATE_TABLES(CULTURE_TABLE_NAME);
        public static final String DATABASE_CREATE_LIFESTYLE = CREATE_TABLES(LIFESTYLE_TABLE_NAME);
        public static final String DATABASE_CREATE_SEARCH = CREATE_TABLES(SEARCH_TABLE_NAME);
        public static final String DATABASE_CREATE_FAVORITE = CREATE_TABLES(FAVORITE_TABLE_NAME);

        public static void onCreate (SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_WORLD);
            db.execSQL(DATABASE_CREATE_SCIENCE);
            db.execSQL(DATABASE_CREATE_SPORT);
            db.execSQL(DATABASE_CREATE_CULTURE);
            db.execSQL(DATABASE_CREATE_LIFESTYLE);
            db.execSQL(DATABASE_CREATE_SEARCH);
            db.execSQL(DATABASE_CREATE_FAVORITE);
            db.execSQL(CREATE_QUOTE_TABLE());
        }

        public static void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + WORLD_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SCIENCE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SPORT_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + CULTURE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + LIFESTYLE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SEARCH_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + QUOTE_TABLE_NAME);
            onCreate(db);
        }

        private static String CREATE_TABLES(String tableName) {
            return "create table " +
                    tableName +
                    "(" +
                    _ID +
                    " integer primary key autoincrement, " +
                    COLUMN_NAME_DATE +
                    " text not null, " +
                    COLUMN_NAME_TITLE +
                    " text not null, " +
                    COLUMN_NAME_THUMB +
                    " blob, " +
                    COLUMN_NAME_CONTENT +
                    " text not null, " +
                    COLUMN_NAME_TAG +
                    " text not null, " +
                    COLUMN_FAVORITED +
                    " long not null, " +
                    COLUMN_NAME_WEBADDRESS +
                    " text not null);";
        }
        private static String CREATE_QUOTE_TABLE() {
            return "create table " +
                    QUOTE_TABLE_NAME +
                    "(" +
                    _ID +
                    " integer primary key autoincrement, " +
                    QUOTE_TEXT +
                    " text not null, " +
                    QUOTE_AUTHOR +
                    " text not null)";
        }

    }



}
