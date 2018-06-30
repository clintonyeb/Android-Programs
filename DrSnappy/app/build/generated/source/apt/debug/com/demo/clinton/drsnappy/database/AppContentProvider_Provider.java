package com.demo.clinton.drsnappy.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.runtime.BaseContentProvider;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import java.lang.IllegalArgumentException;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class AppContentProvider_Provider extends BaseContentProvider {
  private static final String AUTHORITY = "com.demo.clinton.drsnappy.provider";

  private static final int Snapshot_SNAP_SHOT_URI = 0;

  private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
  static {
  MATCHER.addURI(AUTHORITY, "Snapshot", Snapshot_SNAP_SHOT_URI);
  }
  ;

  @Override
  public final String getDatabaseName() {
    return "AppDatabase";
  }

  @Override
  public final String getType(Uri uri) {
    String type = null;
    switch(MATCHER.match(uri)) {
      case Snapshot_SNAP_SHOT_URI: {
        type = "vnd.android.cursor.dir/Snapshot";
        break;
      }
      default: {
        throw new IllegalArgumentException("Unknown URI" + uri);
      }
    }
    return type;
  }

  @Override
  public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    android.database.Cursor cursor = null;
    switch(MATCHER.match(uri)) {
      case Snapshot_SNAP_SHOT_URI: {
        cursor = FlowManager.getDatabase("AppDatabase").getWritableDatabase().query("Snapshot", projection, selection, selectionArgs, null, null, sortOrder);
        break;
      }
    }
    if (cursor != null) {
      cursor.setNotificationUri(getContext().getContentResolver(), uri);
    }
    return cursor;
  }

  @Override
  public final Uri insert(Uri uri, ContentValues values) {
    switch(MATCHER.match(uri)) {
      case Snapshot_SNAP_SHOT_URI: {
        ModelAdapter adapter = FlowManager.getModelAdapter(FlowManager.getTableClassForName("AppDatabase", "Snapshot"));
        final long id = FlowManager.getDatabase("AppDatabase").getWritableDatabase().insertWithOnConflict("Snapshot", null, values, ConflictAction.getSQLiteDatabaseAlgorithmInt(adapter.getInsertOnConflictAction()));
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
      }
      default: {
        throw new IllegalArgumentException("Unknown URI" + uri);
      }
    }
  }

  @Override
  protected final int bulkInsert(Uri uri, ContentValues values) {
    switch(MATCHER.match(uri)) {
      case Snapshot_SNAP_SHOT_URI: {
        ModelAdapter adapter = FlowManager.getModelAdapter(FlowManager.getTableClassForName("AppDatabase", "Snapshot"));
        final long id = FlowManager.getDatabase("AppDatabase").getWritableDatabase().insertWithOnConflict("Snapshot", null, values, ConflictAction.getSQLiteDatabaseAlgorithmInt(adapter.getInsertOnConflictAction()));
        getContext().getContentResolver().notifyChange(uri, null);
        return id > 0 ? 1 : 0;
      }
      default: {
        throw new IllegalArgumentException("Unknown URI" + uri);
      }
    }
  }

  @Override
  public final int delete(Uri uri, String selection, String[] selectionArgs) {
    switch(MATCHER.match(uri)) {
      case Snapshot_SNAP_SHOT_URI: {
        long count = FlowManager.getDatabase("AppDatabase").getWritableDatabase().delete("Snapshot", selection, selectionArgs);
        if (count > 0) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return (int) count;
      }
      default: {
        throw new IllegalArgumentException("Unknown URI" + uri);
      }
    }
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    switch(MATCHER.match(uri)) {
      case Snapshot_SNAP_SHOT_URI: {
        ModelAdapter adapter = FlowManager.getModelAdapter(FlowManager.getTableClassForName("AppDatabase", "Snapshot"));
        long count = FlowManager.getDatabase("AppDatabase").getWritableDatabase().updateWithOnConflict("Snapshot", values, selection, selectionArgs, ConflictAction.getSQLiteDatabaseAlgorithmInt(adapter.getUpdateOnConflictAction()));
        if (count > 0) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return (int) count;
      }
      default: {
        throw new IllegalArgumentException("Unknown URI" + uri);
      }
    }
  }
}
