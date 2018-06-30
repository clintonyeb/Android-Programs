package com.example.holys.databasedemo;

import android.provider.BaseColumns;

/**
 * Created by holys on 2/15/2016.
 */
public class FeedReaderContract {
    public FeedReaderContract()
    {

    }

    public static abstract class FeedEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_ENTRY_TITLE  = "title";
        public static final String COLUMN_NAME_ENTRY_SUBTITLE = "subtitle";
    }
}
