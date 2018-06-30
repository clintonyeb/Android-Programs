import android.provider.BaseColumns;

/**
 * Created by holys on 2/12/2016.
 */
public final class MyDatabaseContract {
    public MyDatabaseContract()
    {}

    public static abstract class DatabaseEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "myentry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
