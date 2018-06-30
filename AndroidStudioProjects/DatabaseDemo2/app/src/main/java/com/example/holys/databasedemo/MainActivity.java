package com.example.holys.databasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    FeedReaderDbHelper reader;
    SQLiteDatabase database;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void InsertOnClick(View view)
    {
        DoEntry();
    }

    public void RetrieveOnClick(View view)
    {
        Retrieve();
    }

    private void DoEntry()
    {
        reader = new FeedReaderDbHelper(this);
        database = reader.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, "1");
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_TITLE, "Title1");
        //values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_SUBTITLE, "subtitle1");

        long rowId = database.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        Toast.makeText(MainActivity.this, "Inserted " + rowId, Toast.LENGTH_SHORT).show();
    }

    private void Retrieve()
    {
        Cursor cursor = database.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        String ItemId = cursor.getString(
                cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_TITLE)
        );

        
    }
}
