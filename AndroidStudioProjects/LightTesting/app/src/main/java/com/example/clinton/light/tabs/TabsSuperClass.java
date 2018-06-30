package com.example.clinton.light.tabs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.clinton.light.R;
import com.example.clinton.light.newsdir.EndlessRecyclerViewScrollListener;
import com.example.clinton.light.newsdir.MyListCursorAdapter;


public abstract class TabsSuperClass extends Fragment {


    public MyListCursorAdapter mAdapter;
    public RecyclerView recyclerView;
    public Button mButton;
    public SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLinear;
    protected ProgressBar progressBar;

    View rootView;

    public static int pageNumber = 1;
    public boolean _isLoading = false;
    public boolean CLEAR ;
    public boolean _shdClear = true;

    public int DATA = 0;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
        InitializeViews();

        pageNumber = 1;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                CLEAR = false;
                mButton.setVisibility(View.GONE);
                pageNumber++;
                DATA  = mAdapter.getItemCount();
                CallFetchData();
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinear) {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
		        if (mButton.getVisibility() == View.GONE &&
				        !_isLoading &&
				        mAdapter.getItemCount() <= 25) {
			        mButton.setVisibility(View.VISIBLE);
		        }

	        }
        });
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setColorSchemeResources(R.color.guardian_Color, R.color.colorAccent, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
	        @Override
	        public void onRefresh() {
		        if (_shdClear) {
			        pageNumber = 1;
			        CLEAR = true;
			        _shdClear = false;
			        CallFetchData();
		        } else {
			        swipeRefreshLayout.setRefreshing(false);
		        }


	        }
        });
        mAdapter = new MyListCursorAdapter(this.getContext(), null);
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void InitializeViews()
    {
        recyclerView = (RecyclerView)rootView.findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(true);
        mLinear  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinear);
        mButton = (Button)rootView.findViewById(R.id.loadButton);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
    }

    private void InitializeStaticMembers()
    {

    }
    public abstract void CallFetchData();

    public void WaitSomeTime()
    {
        new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                //Log.v("TIMER", "seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                _shdClear = true;
            }
        }.start();
    }

    protected   boolean CheckConnectionState()
    {
        ConnectivityManager manager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info .isConnected())
        {
            return true;
        }
        else
            return false;
    }

}
