package com.example.clinton.light.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by clinton on 2/26/2016.
 */
public class NewsContentProvider extends ContentProvider {

    private NewsDataHelper newsDataHelper;


    public boolean onCreate () {
        newsDataHelper = new NewsDataHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query (Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        checkColumns(projection);

        int uriType = NewsContract.sURI_MATCHER.match(uri);

        switch (uriType) {
            case NewsContract.WORLD_ID:
                sqLiteQueryBuilder.appendWhere(NewsContract.DataContract._ID + "=" + uri.getLastPathSegment());
            case NewsContract.WORLD:
                sqLiteQueryBuilder.setTables(NewsContract.DataContract.WORLD_TABLE_NAME);
                break;
            case NewsContract.SCIENCE_ID:
                sqLiteQueryBuilder.appendWhere(NewsContract.DataContract._ID + "=" + uri.getLastPathSegment());
            case NewsContract.SCIENCE:
                sqLiteQueryBuilder.setTables(NewsContract.DataContract.SCIENCE_TABLE_NAME);
                break;
            case NewsContract.SPORT_ID:
                sqLiteQueryBuilder.appendWhere(NewsContract.DataContract._ID + "=" + uri.getLastPathSegment());
            case NewsContract.SPORT:
                sqLiteQueryBuilder.setTables(NewsContract.DataContract.SPORT_TABLE_NAME);
                break;
            case NewsContract.CULTURE_ID:
                sqLiteQueryBuilder.appendWhere(NewsContract.DataContract._ID + "=" + uri.getLastPathSegment());
            case NewsContract.CULTURE:
                sqLiteQueryBuilder.setTables(NewsContract.DataContract.CULTURE_TABLE_NAME);
                break;
            case NewsContract.LIFESTYLE_ID:
                sqLiteQueryBuilder.appendWhere(NewsContract.DataContract._ID + "=" + uri.getLastPathSegment());
            case NewsContract.LIFESTYLE:
                sqLiteQueryBuilder.setTables(NewsContract.DataContract.LIFESTYLE_TABLE_NAME);
                break;
            case NewsContract.SEARCH_ID:
                sqLiteQueryBuilder.appendWhere(NewsContract.DataContract._ID + "=" + uri.getLastPathSegment());
            case NewsContract.SEARCH:
                sqLiteQueryBuilder.setTables(NewsContract.DataContract.SEARCH_TABLE_NAME);
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
        int uriType = NewsContract.sURI_MATCHER.match(uri);
        SQLiteDatabase db = newsDataHelper.getWritableDatabase();
        long id = 0;
        Uri _uri = null;
        switch (uriType) {
            case NewsContract.WORLD:
                id = db.insert(NewsContract.DataContract.WORLD_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(NewsContract.CONTENT_URI_WORLD, id);
                break;
            case NewsContract.SCIENCE:
                id = db.insert(NewsContract.DataContract.SCIENCE_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(NewsContract.CONTENT_URI_SCIENCE, id);
                break;
            case NewsContract.SPORT:
                id = db.insert(NewsContract.DataContract.SPORT_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(NewsContract.CONTENT_URI_SPORT, id);
                break;
            case NewsContract.CULTURE:
                id = db.insert(NewsContract.DataContract.CULTURE_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(NewsContract.CONTENT_URI_CULTURE, id);
                break;
            case NewsContract.LIFESTYLE:
                id = db.insert(NewsContract.DataContract.LIFESTYLE_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(NewsContract.CONTENT_URI_LIFESTYLE, id);
                break;
            case NewsContract.SEARCH:
                id = db.insert(NewsContract.DataContract.SEARCH_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(NewsContract.CONTENT_URI_SEARCH, id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        getContext().getContentResolver().notifyChange(_uri, null);

        return _uri;
    }

    @Override
    public int delete (Uri uri, String selection, String[] selectionArgs) {
        int uriType = NewsContract.sURI_MATCHER.match(uri);
        SQLiteDatabase db = newsDataHelper.getWritableDatabase();
        int rowsDeleted = 0;
        String id = null;
        switch (uriType) {
            case NewsContract.WORLD:
                db.execSQL("DROP TABLE IF EXISTS " + NewsContract.DataContract.WORLD_TABLE_NAME);
                db.execSQL(NewsContract.DataContract.DATABASE_CREATE_WORLD);
                break;
            case NewsContract.WORLD_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(NewsContract.DataContract.WORLD_TABLE_NAME, NewsContract.DataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(NewsContract.DataContract.WORLD_TABLE_NAME, NewsContract.DataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }
                break;
            case NewsContract.SCIENCE:
                db.execSQL("DROP TABLE IF EXISTS " + NewsContract.DataContract.SCIENCE_TABLE_NAME);
                db.execSQL(NewsContract.DataContract.DATABASE_CREATE_SCIENCE);
                break;
            case NewsContract.SCIENCE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(NewsContract.DataContract.SCIENCE_TABLE_NAME, NewsContract.DataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(NewsContract.DataContract.SCIENCE_TABLE_NAME, NewsContract.DataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }
                break;
            case NewsContract.SPORT:
                db.execSQL("DROP TABLE IF EXISTS " + NewsContract.DataContract.SPORT_TABLE_NAME);
                db.execSQL(NewsContract.DataContract.DATABASE_CREATE_SPORT);
                break;
            case NewsContract.SPORT_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(NewsContract.DataContract.SPORT_TABLE_NAME, NewsContract.DataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(NewsContract.DataContract.SPORT_TABLE_NAME, NewsContract.DataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }
                break;
            case NewsContract.CULTURE:
                db.execSQL("DROP TABLE IF EXISTS " + NewsContract.DataContract.CULTURE_TABLE_NAME);
                db.execSQL(NewsContract.DataContract.DATABASE_CREATE_CULTURE);
                break;
            case NewsContract.CULTURE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(NewsContract.DataContract.CULTURE_TABLE_NAME, NewsContract.DataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(NewsContract.DataContract.CULTURE_TABLE_NAME, NewsContract.DataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }
                break;
            case NewsContract.LIFESTYLE:
                db.execSQL("DROP TABLE IF EXISTS " + NewsContract.DataContract.LIFESTYLE_TABLE_NAME);
                db.execSQL(NewsContract.DataContract.DATABASE_CREATE_LIFESTYLE);
                break;
            case NewsContract.LIFESTYLE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(NewsContract.DataContract.LIFESTYLE_TABLE_NAME, NewsContract.DataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(NewsContract.DataContract.LIFESTYLE_TABLE_NAME, NewsContract.DataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }
                break;
            case NewsContract.SEARCH:
                db.execSQL("DROP TABLE IF EXISTS " + NewsContract.DataContract.SEARCH_TABLE_NAME);
                db.execSQL(NewsContract.DataContract.DATABASE_CREATE_SEARCH);
                break;
            case NewsContract.SEARCH_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(NewsContract.DataContract.SEARCH_TABLE_NAME, NewsContract.DataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(NewsContract.DataContract.SEARCH_TABLE_NAME, NewsContract.DataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        //getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update (Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = NewsContract.sURI_MATCHER.match(uri);
        SQLiteDatabase db = newsDataHelper.getWritableDatabase();
        int rowsUpdated = 0;

        String id = null;
        switch (uriType) {
            case NewsContract.WORLD:
                rowsUpdated = db.update(NewsContract.DataContract.WORLD_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case NewsContract.WORLD_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(NewsContract.DataContract.WORLD_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(NewsContract.DataContract.WORLD_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case NewsContract.SPORT:
                rowsUpdated = db.update(NewsContract.DataContract.SPORT_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case NewsContract.SPORT_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(NewsContract.DataContract.SPORT_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(NewsContract.DataContract.SPORT_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case NewsContract.SCIENCE:
                rowsUpdated = db.update(NewsContract.DataContract.SCIENCE_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case NewsContract.SCIENCE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(NewsContract.DataContract.SCIENCE_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(NewsContract.DataContract.SCIENCE_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case NewsContract.CULTURE:
                rowsUpdated = db.update(NewsContract.DataContract.CULTURE_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case NewsContract.CULTURE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(NewsContract.DataContract.CULTURE_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(NewsContract.DataContract.CULTURE_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case NewsContract.LIFESTYLE:
                rowsUpdated = db.update(NewsContract.DataContract.LIFESTYLE_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case NewsContract.LIFESTYLE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(NewsContract.DataContract.LIFESTYLE_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(NewsContract.DataContract.LIFESTYLE_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case NewsContract.SEARCH:
                rowsUpdated = db.update(NewsContract.DataContract.SEARCH_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case NewsContract.SEARCH_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(NewsContract.DataContract.SEARCH_TABLE_NAME,
                            values,
                            NewsContract.DataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(NewsContract.DataContract.SEARCH_TABLE_NAME,
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
                NewsContract.DataContract.COLUMN_NAME_TITLE, NewsContract.DataContract.COLUMN_NAME_THUMB,
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