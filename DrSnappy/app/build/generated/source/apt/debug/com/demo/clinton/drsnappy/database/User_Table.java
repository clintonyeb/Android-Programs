package com.demo.clinton.drsnappy.database;

import android.content.ContentValues;
import android.database.Cursor;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.DatabaseHolder;
import com.raizlabs.android.dbflow.converter.BooleanConverter;
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
import java.lang.Boolean;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Number;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class User_Table extends ModelAdapter<User> {
  /**
   * Primary Key AutoIncrement */
  public static final LongProperty id = new LongProperty(User.class, "id");

  public static final Property<String> username = new Property<String>(User.class, "username");

  public static final Property<String> emailId = new Property<String>(User.class, "emailId");

  public static final Property<String> access_token = new Property<String>(User.class, "access_token");

  public static final TypeConvertedProperty<Boolean, Integer> authenticatedFully = new TypeConvertedProperty<Boolean, Integer>(User.class, "authenticatedFully");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,username,emailId,access_token,authenticatedFully};

  private final BooleanConverter global_typeConverterBooleanConverter;

  public User_Table(DatabaseHolder holder, DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
    global_typeConverterBooleanConverter = (BooleanConverter) holder.getTypeConverterForClass(Boolean.class);
  }

  @Override
  public final Class<User> getModelClass() {
    return User.class;
  }

  public final String getTableName() {
    return "`User`";
  }

  @Override
  public final BaseProperty getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch (columnName)  {
      case "`id`":  {
        return id;
      }
      case "`username`":  {
        return username;
      }
      case "`emailId`":  {
        return emailId;
      }
      case "`access_token`":  {
        return access_token;
      }
      case "`authenticatedFully`":  {
        return authenticatedFully;
      }
      default:  {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  public final void updateAutoIncrement(User model, Number id) {
    model.id = id.longValue();
  }

  @Override
  public final Number getAutoIncrementingId(User model) {
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
  public final void bindToInsertValues(ContentValues values, User model) {
    values.put("username", model.username != null ? model.username : null);
    values.put("emailId", model.emailId != null ? model.emailId : null);
    values.put("access_token", model.access_token != null ? model.access_token : null);
    Integer refauthenticatedFully = model.authenticatedFully != null ? global_typeConverterBooleanConverter.getDBValue(model.authenticatedFully) : null;
    values.put("authenticatedFully", refauthenticatedFully != null ? refauthenticatedFully : null);
  }

  @Override
  public final void bindToContentValues(ContentValues values, User model) {
    values.put("id", model.id);
    bindToInsertValues(values, model);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, User model, int start) {
    if (model.username != null)  {
      statement.bindString(1 + start, model.username);
    } else {
      statement.bindNull(1 + start);
    }
    if (model.emailId != null)  {
      statement.bindString(2 + start, model.emailId);
    } else {
      statement.bindNull(2 + start);
    }
    if (model.access_token != null)  {
      statement.bindString(3 + start, model.access_token);
    } else {
      statement.bindNull(3 + start);
    }
    Integer refauthenticatedFully = model.authenticatedFully != null ? global_typeConverterBooleanConverter.getDBValue(model.authenticatedFully) : null;
    if (refauthenticatedFully != null)  {
      statement.bindLong(4 + start, refauthenticatedFully);
    } else {
      statement.bindNull(4 + start);
    }
  }

  @Override
  public final void bindToStatement(DatabaseStatement statement, User model) {
    int start = 0;
    statement.bindLong(1 + start, model.id);
    bindToInsertStatement(statement, model, 1);
  }

  @Override
  public final String getInsertStatementQuery() {
    return "INSERT INTO `User`(`username`,`emailId`,`access_token`,`authenticatedFully`) VALUES (?,?,?,?)";
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `User`(`id`,`username`,`emailId`,`access_token`,`authenticatedFully`) VALUES (?,?,?,?,?)";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `User`(`id` INTEGER PRIMARY KEY AUTOINCREMENT,`username` TEXT,`emailId` TEXT,`access_token` TEXT,`authenticatedFully` INTEGER" + ");";
  }

  @Override
  public final void loadFromCursor(Cursor cursor, User model) {
    int index_id = cursor.getColumnIndex("id");
    if (index_id != -1 && !cursor.isNull(index_id)) {
      model.id = cursor.getLong(index_id);
    } else {
      model.id = 0;
    }
    int index_username = cursor.getColumnIndex("username");
    if (index_username != -1 && !cursor.isNull(index_username)) {
      model.username = cursor.getString(index_username);
    } else {
      model.username = null;
    }
    int index_emailId = cursor.getColumnIndex("emailId");
    if (index_emailId != -1 && !cursor.isNull(index_emailId)) {
      model.emailId = cursor.getString(index_emailId);
    } else {
      model.emailId = null;
    }
    int index_access_token = cursor.getColumnIndex("access_token");
    if (index_access_token != -1 && !cursor.isNull(index_access_token)) {
      model.access_token = cursor.getString(index_access_token);
    } else {
      model.access_token = null;
    }
    int index_authenticatedFully = cursor.getColumnIndex("authenticatedFully");
    if (index_authenticatedFully != -1 && !cursor.isNull(index_authenticatedFully)) {
      model.authenticatedFully = global_typeConverterBooleanConverter.getModelValue(cursor.getInt(index_authenticatedFully));
    } else {
      model.authenticatedFully = global_typeConverterBooleanConverter.getModelValue(null);
    }
  }

  @Override
  public final boolean exists(User model, DatabaseWrapper wrapper) {
    return model.id > 0
    && SQLite.selectCountOf()
    .from(User.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final ConditionGroup getPrimaryConditionClause(User model) {
    ConditionGroup clause = ConditionGroup.clause();
    clause.and(id.eq(model.id));
    return clause;
  }

  @Override
  public final User newInstance() {
    return new User();
  }
}
