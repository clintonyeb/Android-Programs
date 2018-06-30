package com.example.holys.rssreader;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RSSFeed extends AppCompatActivity implements ListFragement.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed);
    }

    @Override
    public void onFragmentInteraction(String link) {
        DetailFragment fragment = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        fragment.setText(link);
    }
}
