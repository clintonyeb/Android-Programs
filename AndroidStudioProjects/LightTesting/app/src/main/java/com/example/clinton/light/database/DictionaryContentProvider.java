package com.example.clinton.light.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DictionaryContentProvider extends ContentProvider {
    DictionaryHelper dictionaryHelper;
    public DictionaryContentProvider () {}

    @Override
    public int delete (Uri uri, String selection, String[] selectionArgs) {
        int uriType = DictionaryContract.sURI_MATCHER.match(uri);
        SQLiteDatabase db = dictionaryHelper.getWritableDatabase();
        int rowsDeleted = 0;
        String id = null;
        switch (uriType)
        {
            case DictionaryContract.TODAY:
                db.execSQL("DROP TABLE IF EXISTS " + DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME);
                db.execSQL(DictionaryContract.DictionaryDataContract.CREATE_TODAY_TABLE);
                break;
            case DictionaryContract.TODAY_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.TODAY_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }
                break;
            case DictionaryContract.FAVORITE:
                rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME, selection, selectionArgs);
                break;
            case DictionaryContract.FAVORITE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.FAVORITE_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }
                break;
            case DictionaryContract.RECENT:
                rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME, selection, selectionArgs);
                break;
            case DictionaryContract.RECENT_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.RECENT_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }

                break;
            case DictionaryContract.OTHER:
                //rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME, selection, selectionArgs);
                db.execSQL("DROP TABLE IF EXISTS " + DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME);
                db.execSQL(DictionaryContract.DictionaryDataContract.CREATE_OTHER_TABLE);
                break;
            case DictionaryContract.OTHER_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }

                break;
            case DictionaryContract.RANDOM:
                //rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME, selection, selectionArgs);
                db.execSQL("DROP TABLE IF EXISTS " + DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME);
                db.execSQL(DictionaryContract.DictionaryDataContract.CREATE_RANDOM_TABLE);
                break;
            case DictionaryContract.RANDOM_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.RANDOM_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id + " and" +
                            selection, selectionArgs);
                }

                break;

            case DictionaryContract.DICTIONARY:
                db.execSQL("DROP TABLE IF EXISTS " + DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME);
                db.execSQL(DictionaryContract.DictionaryDataContract.CREATE_DICTIONARY_TABLE);
                break;
            case DictionaryContract.DICTIONARY_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(DictionaryContract.DictionaryDataContract.DICTIONARY_TABLE_NAME, DictionaryContract.DictionaryDataContract._ID + "=" + id + " and" +
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
    public String getType (Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert (Uri uri, ContentValues values) {
        int uriType = DictionaryContract.sURI_MATCHER.match(uri);
        SQLiteDatabase db = dictionaryHelper.getWritableDatabase();
        long id = 0;
        Uri _uri = null;
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
            case DictionaryContract.OTHER:
                id = db.insert(DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME, null, values);
                _uri = ContentUris.withAppendedId(DictionaryContract.CONTENT_URI_OTHER, id);
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
    public Cursor query (Uri uri, String[] projection, String selection,
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
            case DictionaryContract.OTHER_ID:
                sqLiteQueryBuilder.appendWhere(DictionaryContract.DictionaryDataContract._ID + "=" + uri.getLastPathSegment());
            case DictionaryContract.OTHER:
                sqLiteQueryBuilder.setTables(DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME);
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
    public int update (Uri uri, ContentValues values, String selection,
                       String[] selectionArgs) {
        int uriType = DictionaryContract.sURI_MATCHER.match(uri);
        SQLiteDatabase db = dictionaryHelper.getWritableDatabase();
        int rowsUpdated = 0;

        String id = null;
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
            case DictionaryContract.OTHER:
                rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case DictionaryContract.OTHER_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME,
                            values,
                            DictionaryContract.DictionaryDataContract._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(DictionaryContract.DictionaryDataContract.OTHER_TABLE_NAME,
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
