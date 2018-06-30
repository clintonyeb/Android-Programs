package com.example.holys.intentreceivers;

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

    public void SendBroadCast(View view)
    {
        Intent intent = new Intent();
        intent.setAction("clinton.CUSTOM_INTENT");
        sendBroadcast(intent);
    }
}
