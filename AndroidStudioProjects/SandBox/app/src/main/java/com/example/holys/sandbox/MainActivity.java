package com.example.holys.sandbox;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        listView = (ListView)findViewById(R.id.myList);
        String[] listStrings = {"item1", "item2", "item3", "item4"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listStrings);
        listView.setAdapter(adapter);

        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("skipWelcome", false)
                ) {

            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
