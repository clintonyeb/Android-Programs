package com.example.holys.androidservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void StartServices(View view)
    {
        startService(new Intent(getBaseContext(), MyService.class));
    }

    public  void StopServices(View view)
    {
        stopService(new Intent(getBaseContext(), MyService.class));
    }
}
