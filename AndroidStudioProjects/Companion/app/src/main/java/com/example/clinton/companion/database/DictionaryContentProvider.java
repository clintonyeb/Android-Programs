package com.example.clinton.companion.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class DictionaryContentProvider extends ContentProvider {
    DictionaryHelper dictionaryHelper;
    public DictionaryContentProvider() {}

    @Override
    public int delete (@NonNull Uri uri, String selection, String[] selectionArgs) {
        int uriType = DictionaryContract.sURI_MATCHER.match(uri);
        int rowsDeleted;
        String id;
        String TABLE_NAME;
        switch (uriType)
        {
            case DictionaryContract.TODAY:
                TABLE_NAME = DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME);
                break;
            case DictionaryContract.TODAY_ID:
                id = uri.getLastPathSegment();
                TABLE_NAME = DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME, id);
                break;
            case DictionaryContract.FAVORITE:
                TABLE_NAME = DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME);
                break;
            case DictionaryContract.FAVORITE_ID:
                id = uri.getLastPathSegment();
                TABLE_NAME = DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME, id);
                break;
            case DictionaryContract.RECENT:
                TABLE_NAME = DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME);
                break;
            case DictionaryContract.RECENT_ID:
                id = uri.getLastPathSegment();
                TABLE_NAME = DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME, id);
                break;
            case DictionaryContract.RELATED:
                TABLE_NAME = DictionaryContract.DictionaryDataContract.RELATED_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME);
                break;
            case DictionaryContract.RELATED_ID:
                id = uri.getLastPathSegment();
                TABLE_NAME = DictionaryContract.DictionaryDataContract.RELATED_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME, id);

                break;
            case DictionaryContract.RANDOM:
                TABLE_NAME = DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME);
                break;
            case DictionaryContract.RANDOM_ID:
                id = uri.getLastPathSegment();
                TABLE_NAME = DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME, id);
                break;

            case DictionaryContract.DICTIONARY:
                TABLE_NAME = DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME);
                break;
            case DictionaryContract.DICTIONARY_ID:
                id = uri.getLastPathSegment();
                TABLE_NAME = DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME;
                rowsDeleted = delete(selection, selectionArgs, TABLE_NAME, id);

                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    private int delete(String selection, String[] selectionArgs, String TableName)
    {
        SQLiteDatabase db = dictionaryHelper.getWritableDatabase();
        int rowsDeleted = db.delete(TableName, selection, selectionArgs);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TableName + "'");
        return rowsDeleted;
    }

    private int delete(String selection, String[] selectionArgs, String TableName, String rowId)
    {
        SQLiteDatabase db = dictionaryHelper.getWritableDatabase();
        int rowsDeleted;
        if (TextUtils.isEmpty(selection)) {
            rowsDeleted = db.delete(TableName, DictionaryContract.DictionaryDataContract._ID + "=" + rowId, null);
        } else {
            rowsDeleted = db.delete(TableName, DictionaryContract.DictionaryDataContract._ID + "=" + rowId + " and" +
                    selection, selectionArgs);
        }
        return rowsDeleted;
    }

    @Override
    public String getType (@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert (@NonNull Uri uri, ContentValues values) {
        int uriType = DictionaryContract.sURI_MATCHER.match(uri);
        SQLiteDatabase db = dictionaryHelper.getWritableDatabase();
        long id;
        Uri _uri;
        switch (uriType)
        {
            case DictionaryContract.TODAY:
                id = db.insert(DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(DictionaryContract.CONTENT_URI_TODAY, id);
                break;
            case DictionaryContract.FAVORITE:
                id = db.insert(DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(DictionaryContract.CONTENT_URI_FAVORITES, id);
                break;
            case DictionaryContract.RECENT:
                id = db.insert(DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(DictionaryContract.CONTENT_URI_RECENT, id);
                break;
            case DictionaryContract.RELATED:
                id = db.insert(DictionaryContract.DictionaryDataContract.RELATED_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(DictionaryContract.CONTENT_URI_RELATED, id);
                break;
            case DictionaryContract.RANDOM:
                id = db.insert(DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(DictionaryContract.CONTENT_URI_RANDOM, id);
                break;
            case DictionaryContract.DICTIONARY:
                id = db.insert(DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(DictionaryContract.CONTENT_URI_DICTIONARY, id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);

        }

        getContext().getContentResolver().notifyChange(_uri, null);

        return _uri;
    }

    @Override
    public boolean onCreate () {
        dictionaryHelper = new DictionaryHelper(getContext());
        return true;
    }

    @Override
    public Cursor query (@NonNull Uri uri, String[] projection, String selection,
                         String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        int uriType = DictionaryContract.sURI_MATCHER.match(uri);
        switch (uriType)
        {
            case DictionaryContract.TODAY_ID:
                sqLiteQueryBuilder.appendWhere(DictionaryContract.DictionaryDataContract._ID + "=" + uri.getLastPathSegment());
            case DictionaryContract.TODAY:
                sqLiteQueryBuilder.setTables(DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME);
                break;
            case DictionaryContract.FAVORITE_ID:
                sqLiteQueryBuilder.appendWhere(DictionaryContract.DictionaryDataContract._ID + "=" + uri.getLastPathSegment());
            case DictionaryContract.FAVORITE:
                sqLiteQueryBuilder.setTables(DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME);
                break;
            case DictionaryContract.RECENT_ID:
                sqLiteQueryBuilder.appendWhere(DictionaryContract.DictionaryDataContract._ID + "=" + uri.getLastPathSegment());
            case DictionaryContract.RECENT:
                sqLiteQueryBuilder.setTables(DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME);
                break;
            case DictionaryContract.RELATED_ID:
                sqLiteQueryBuilder.appendWhere(DictionaryContract.DictionaryDataContract._ID + "=" + uri.getLastPathSegment());
            case DictionaryContract.RELATED:
                sqLiteQueryBuilder.setTables(DictionaryContract.DictionaryDataContract.RELATED_TABLE_NAME);
                break;
            case DictionaryContract.RANDOM_ID:
                sqLiteQueryBuilder.appendWhere(DictionaryContract.DictionaryDataContract._ID + "=" + uri.getLastPathSegment());
            case DictionaryContract.RANDOM:
                sqLiteQueryBuilder.setTables(DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME);
                break;
            case DictionaryContract.DICTIONARY_ID:
                sqLiteQueryBuilder.appendWhere(DictionaryContract.DictionaryDataContract._ID + "=" + uri.getLastPathSegment());
            case DictionaryContract.DICTIONARY:
                sqLiteQueryBuilder.setTables(DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        Cursor cursor = sqLiteQueryBuilder.query(dictionaryHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update (@NonNull Uri uri, ContentValues values, String selection,
                       String[] selectionArgs) {
        int uriType = DictionaryContract.sURI_MATCHER.match(uri);
        SQLiteDatabase db = dictionaryHelper.getWritableDatabase();
        int rowsUpdated;

        String id;
        switch (uriType)
        {
            case DictionaryContract.TODAY:
                rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case DictionaryContract.TODAY_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + " = " + id,
                            null);
                } else {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + " = " + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case DictionaryContract.FAVORITE:
                rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case DictionaryContract.FAVORITE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + " = " + id,
                            null);
                } else {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + " = " + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case DictionaryContract.RECENT:
                rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case DictionaryContract.RECENT_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + "=" + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case DictionaryContract.RELATED:
                rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.RELATED_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case DictionaryContract.RELATED_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.RELATED_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.RELATED_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + "=" + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case DictionaryContract.DICTIONARY:
                rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case DictionaryContract.DICTIONARY_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + "=" + id
                                    + " and "
                                    + selection, selectionArgs);
                }
                break;
            case DictionaryContract.RANDOM:
                rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case DictionaryContract.RANDOM_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + "=" + id
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
}
