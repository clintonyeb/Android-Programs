package com.example.holys.light.DATABASE_DIR;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by holys on 2/26/2016.
 */
public class NewsContentProvider extends ContentProvider {

    private NewsDataHelper newsDataHelper;
    private static final int NEWS = 10;
    private static final int NEWS_ID = 20;
    private static final String AUTHORITY = "com.example.holys.light.provider";
    private static final String BASE_PATH = "news";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    private static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/news";
    private static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/news";
    private static final UriMatcher sURI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH, NEWS);
        sURI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/#", NEWS_ID);
    }

    public boolean onCreate () {
        newsDataHelper = new NewsDataHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query (Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        checkColumns(projection);
        sqLiteQueryBuilder.setTables(NewsContract.DataContract.TABLE_NAME);
        int uriType = sURI_MATCHER.match(uri);

        switch (uriType) {
            case NEWS_ID:
                sqLiteQueryBuilder.appendWhere(NewsContract.DataContract._ID + "=" + uri.getLastPathSegment());
                break;
            case NEWS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        Cursor cursor = sqLiteQueryBuilder.query(newsDataHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType (Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert (Uri uri, ContentValues values) {
        if (uri == null || values == null) {
            throw new IllegalArgumentException("Unknown URI" + uri);
        }
        int uriType = sURI_MATCHER.match(uri);
        SQLiteDatabase db = newsDataHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case NEWS:
                id = db.insert(NewsContract.DataContract.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete (Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURI_MATCHER.match(uri);
        SQLiteDatabase db = newsDataHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case NEWS:
                rowsDeleted = db.delete(NewsContract.DataContract.TABLE_NAME, selection, selectionArgs);
                break;
            case NEWS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(NewsContract.DataContract.TABLE_NAME, NewsContract.DataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(NewsContract.DataContract.TABLE_NAME, NewsContract.DataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update (Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURI_MATCHER.match(uri);
        SQLiteDatabase db = newsDataHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case NEWS:
                rowsUpdated = db.update(NewsContract.DataContract.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case NEWS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(NewsContract.DataContract.TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(NewsContract.DataContract.TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    private void checkColumns (String[] projection) {
        String[] available = {NewsContract.DataContract.COLUMN_NAME_DATE,
                NewsContract.DataContract.COLUMN_NAME_TITLE, NewsContract.DataContract.COLUMN_NAME_THUMB_SOURCE,
                NewsContract.DataContract.COLUMN_NAME_WEBADDRESS, NewsContract.DataContract.COLUMN_NAME_CONTENT,
                NewsContract.DataContract.COLUMN_NAME_TAG};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available 
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}