package com.example.clinton.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int MAIN_ACTIVITY = 0x763;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, DetailActivity.class);

        startActivityForResult(intent, MAIN_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAIN_ACTIVITY){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Result returned " + data.getStringExtra(DetailActivity.EXTRA_DATA), Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Data Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
