package com.example.holys.bgcclms;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsDataDisplay extends AppCompatActivity {

    List<NewsDataFacade> mNewsData;
    DisplayCustomAdapter mAdapter;
    RecyclerView recyclerView;
    GetJSONData mAsyncTodo;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_data_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.DataToolBar);
        setSupportActionBar(toolbar);
        //ImageButton button = (ImageButton)findViewById(R.id.imageButton);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.HotPink, R.color.colorAccent, R.color.lightBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                mAsyncTodo = new GetJSONData();
                mAsyncTodo.execute(NewsDataDisplay.this);
            }
        });
        Intent intent = getIntent();
        //Integer val = intent.getIntExtra(intent.EXTRA_TEXT, 0);
        //Toast.makeText(NewsDataDisplay.this, String.valueOf(val), Toast.LENGTH_SHORT).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linear  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linear);

        mNewsData = new ArrayList<>();
        mAdapter = new DisplayCustomAdapter(mNewsData, NewsDataDisplay.this);
        recyclerView.setAdapter(mAdapter);
        if (CheckConnectionState())
        {
            mAsyncTodo = new GetJSONData();
            mAsyncTodo.execute(this);
        }
        else
            Toast.makeText(this, "You are offline", Toast.LENGTH_LONG).show();
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
}
