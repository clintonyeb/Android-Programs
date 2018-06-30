package com.raizlabs.android.dbflow.config;

import com.demo.clinton.drsnappy.database.AppDatabase;
import com.demo.clinton.drsnappy.database.Snapshot;
import com.demo.clinton.drsnappy.database.Snapshot_Table;
import com.demo.clinton.drsnappy.database.User;
import com.demo.clinton.drsnappy.database.User_Table;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class AppDatabaseAppDatabase_Database extends DatabaseDefinition {
  public AppDatabaseAppDatabase_Database(DatabaseHolder holder) {
    holder.putDatabaseForTable(User.class, this);
    holder.putDatabaseForTable(Snapshot.class, this);
    models.add(User.class);
    modelTableNames.put("User", User.class);
    modelAdapters.put(User.class, new User_Table(holder, this));
    models.add(Snapshot.class);
    modelTableNames.put("Snapshot", Snapshot.class);
    modelAdapters.put(Snapshot.class, new Snapshot_Table(holder, this));
  }

  @Override
  public final Class<?> getAssociatedDatabaseClassFile() {
    return AppDatabase.class;
  }

  @Override
  public final boolean isForeignKeysSupported() {
    return false;
  }

  @Override
  public final boolean isInMemory() {
    return false;
  }

  @Override
  public final boolean backupEnabled() {
    return false;
  }

  @Override
  public final boolean areConsistencyChecksEnabled() {
    return false;
  }

  @Override
  public final int getDatabaseVersion() {
    return 1;
  }

  @Override
  public final String getDatabaseName() {
    return "AppDatabase";
  }
}
