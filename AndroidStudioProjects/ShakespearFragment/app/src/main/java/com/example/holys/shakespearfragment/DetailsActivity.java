package com.example.holys.shakespearfragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }
        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            DetailFragmentClass details = new DetailFragmentClass();
            details.setArguments(getIntent().getExtras());
            //getFragmentManager().beginTransaction().add(android.R.layout.list_content, details).commit();
        }
    }
}
