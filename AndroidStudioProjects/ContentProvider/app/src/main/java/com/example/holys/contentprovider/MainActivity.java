package com.example.holys.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AddButtonClick(View view)
    {
        ContentValues values = new ContentValues();
        values.put(MyContentProvider.ROLL_NUMBER, ((EditText)findViewById(R.id.roll_number)).getText().toString());
        values.put(MyContentProvider.NAME, ((EditText)findViewById(R.id.name)).getText().toString());
        values.put(MyContentProvider.STUDENT_CLASS, ((EditText)findViewById(R.id.student_class)).getText().toString());
        values.put(MyContentProvider.GRADE, ((EditText)findViewById(R.id.grade)).getText().toString());
        
        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);

        Toast.makeText(MainActivity.this, uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void RetrieveButtonClick(View view)
    {
        String uri = "content:/StudentsDataBase/students";
        Uri URI = Uri.parse(uri);
        Cursor cursor = managedQuery(students, null, null, null, "name");
        if (cursor.moveToFirst())
        {
            do{
                TextView text = (TextView)findViewById(R.id.textView);
                text.setText(cursor.getString(cursor.getColumnIndex(MyContentProvider.GRADE)));
            }while (cursor.moveToNext());
        }

    }
}
