package com.demo.clinton.drsnappy.database;

import android.content.ContentValues;
import android.database.Cursor;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.DatabaseHolder;
import com.raizlabs.android.dbflow.converter.DateConverter;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.BaseProperty;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.language.property.LongProperty;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.sql.language.property.TypeConvertedProperty;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseStatement;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Number;
import java.lang.Override;
import java.lang.String;
import java.util.Date;

/**
 * This is generated code. Please do not modify */
public final class Snapshot_Table extends ModelAdapter<Snapshot> {
  /**
   * Primary Key AutoIncrement */
  public static final LongProperty id = new LongProperty(Snapshot.class, "id");

  public static final TypeConvertedProperty<Blob, byte[]> imageData = new TypeConvertedProperty<Blob, byte[]>(Snapshot.class, "imageData");

  public static final TypeConvertedProperty<Date, Long> timeStamp = new TypeConvertedProperty<Date, Long>(Snapshot.class, "timeStamp");

  public static final Property<String> location = new Property<String>(Snapshot.class, "location");

  public static final TypeConvertedProperty<SnapState, String> state = new TypeConvertedProperty<SnapState, String>(Snapshot.class, "state");

  public static final LongProperty userId = new LongProperty(Snapshot.class, "userId");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,imageData,timeStamp,location,state,userId};

  private final DateConverter global_typeConverterDateConverter;

  public Snapshot_Table(DatabaseHolder holder, DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
    global_typeConverterDateConverter = (DateConverter) holder.getTypeConverterForClass(Date.class);
  }

  @Override
  public final Class<Snapshot> getModelClass() {
    return Snapshot.class;
  }

  public final String getTableName() {
    return "`Snapshot`";
  }

  @Override
  public final BaseProperty getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch (columnName)  {
      case "`id`":  {
        return id;
      }
      case "`imageData`":  {
        return imageData;
      }
      case "`timeStamp`":  {
        return timeStamp;
      }
      case "`location`":  {
        return location;
      }
      case "`state`":  {
        return state;
      }
      case "`userId`":  {
        return userId;
      }
      default:  {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  public final void updateAutoIncrement(Snapshot model, Number id) {
    model.id = id.longValue();
  }

  @Override
  public final Number getAutoIncrementingId(Snapshot model) {
    return model.id;
  }

  @Override
  public final String getAutoIncrementingColumnName() {
    return "id";
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, Snapshot model) {
    byte[] refimageData = model.imageData != null ? model.imageData.getBlob() : null;
    values.put("imageData", refimageData != null ? refimageData : null);
    Long reftimeStamp = model.timeStamp != null ? global_typeConverterDateConverter.getDBValue(model.timeStamp) : null;
    values.put("timeStamp", reftimeStamp != null ? reftimeStamp : null);
    values.put("location", model.location != null ? model.location : null);
    String refstate = model.state != null ? model.state.name() : null;
    values.put("state", refstate != null ? refstate : null);
    values.put("userId", model.userId);
  }

  @Override
  public final void bindToContentValues(ContentValues values, Snapshot model) {
    values.put("id", model.id);
    bindToInsertValues(values, model);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, Snapshot model, int start) {
    byte[] refimageData = model.imageData != null ? model.imageData.getBlob() : null;
    if (refimageData != null)  {
      statement.bindBlob(1 + start, refimageData);
    } else {
      statement.bindNull(1 + start);
    }
    Long reftimeStamp = model.timeStamp != null ? global_typeConverterDateConverter.getDBValue(model.timeStamp) : null;
    if (reftimeStamp != null)  {
      statement.bindLong(2 + start, reftimeStamp);
    } else {
      statement.bindNull(2 + start);
    }
    if (model.location != null)  {
      statement.bindString(3 + start, model.location);
    } else {
      statement.bindNull(3 + start);
    }
    String refstate = model.state != null ? model.state.name() : null;
    if (refstate != null)  {
      statement.bindString(4 + start, refstate);
    } else {
      statement.bindNull(4 + start);
    }
    statement.bindLong(5 + start, model.userId);
  }

  @Override
  public final void bindToStatement(DatabaseStatement statement, Snapshot model) {
    int start = 0;
    statement.bindLong(1 + start, model.id);
    bindToInsertStatement(statement, model, 1);
  }

  @Override
  public final String getInsertStatementQuery() {
    return "INSERT INTO `Snapshot`(`imageData`,`timeStamp`,`location`,`state`,`userId`) VALUES (?,?,?,?,?)";
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `Snapshot`(`id`,`imageData`,`timeStamp`,`location`,`state`,`userId`) VALUES (?,?,?,?,?,?)";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `Snapshot`(`id` INTEGER PRIMARY KEY AUTOINCREMENT,`imageData` BLOB,`timeStamp` TEXT,`location` TEXT,`state` TEXT,`userId` INTEGER" + ");";
  }

  @Override
  public final void loadFromCursor(Cursor cursor, Snapshot model) {
    int index_id = cursor.getColumnIndex("id");
    if (index_id != -1 && !cursor.isNull(index_id)) {
      model.id = cursor.getLong(index_id);
    } else {
      model.id = 0;
    }
    int index_imageData = cursor.getColumnIndex("imageData");
    if (index_imageData != -1 && !cursor.isNull(index_imageData)) {
      model.imageData = new Blob(cursor.getBlob(index_imageData));
    } else {
      model.imageData = null;
    }
    int index_timeStamp = cursor.getColumnIndex("timeStamp");
    if (index_timeStamp != -1 && !cursor.isNull(index_timeStamp)) {
      model.timeStamp = global_typeConverterDateConverter.getModelValue(cursor.getLong(index_timeStamp));
    } else {
      model.timeStamp = global_typeConverterDateConverter.getModelValue(null);
    }
    int index_location = cursor.getColumnIndex("location");
    if (index_location != -1 && !cursor.isNull(index_location)) {
      model.location = cursor.getString(index_location);
    } else {
      model.location = null;
    }
    int index_state = cursor.getColumnIndex("state");
    if (index_state != -1 && !cursor.isNull(index_state)) {
      model.state = SnapState.valueOf(cursor.getString(index_state));
    } else {
      model.state = null;
    }
    int index_userId = cursor.getColumnIndex("userId");
    if (index_userId != -1 && !cursor.isNull(index_userId)) {
      model.userId = cursor.getLong(index_userId);
    } else {
      model.userId = 0;
    }
  }

  @Override
  public final boolean exists(Snapshot model, DatabaseWrapper wrapper) {
    return model.id > 0
    && SQLite.selectCountOf()
    .from(Snapshot.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final ConditionGroup getPrimaryConditionClause(Snapshot model) {
    ConditionGroup clause = ConditionGroup.clause();
    clause.and(id.eq(model.id));
    return clause;
  }

  @Override
  public final Snapshot newInstance() {
    return new Snapshot();
  }
}
