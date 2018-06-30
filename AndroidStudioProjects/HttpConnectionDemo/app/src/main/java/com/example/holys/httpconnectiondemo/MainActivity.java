package com.example.holys.httpconnectiondemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view)
    {
        TextView text = (TextView)findViewById(R.id.text);

        boolean networkState = CheckConnectionState();
        if (networkState)
        {
            text.setText("Connected");

        }
        else
            text.setText("Offline");
    }

    private  boolean CheckConnectionState()
    {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info .isConnected())
        {
            return true;
        }
        else
            return false;
    }

    private class GetDataAsync extends AsyncTask<String, Void, Void>
    {

        HttpURLConnection connection;
        BufferedReader bReader;
        String result;

        @Override
        protected Void doInBackground(String... params) {
            try
            {
                String urlString = "http://bgcedu.in/";
                Uri uriBuilder = Uri.parse(urlString);
                URL url = new URL(uriBuilder.toString());
            }
        }
    }
}
