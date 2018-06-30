package com.example.clinton.light.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.clinton.light.R;
import com.example.clinton.light.utilities.CustomAdapter;

public class WordsMenu extends AppCompatActivity {

    public ListView listView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_menu);
        MainActivity.SetStrictMode();
        listView =(ListView)findViewById(R.id.words_listview);


        String[] array = new String[]
                {
                        "Word-of-the-Day",
                        "Related Words",
                        "Random Words",
		                "Dictionary",
                        "Your Favorites",
                        "Your Recent Searches"
                };
        int[] imageId = new int[]{
                R.drawable.people,
        };

        CustomAdapter adapter = new CustomAdapter(this, getApplicationContext(), array, imageId);
        listView.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
