package com.example.clinton.light.tabs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Toast;

import com.example.clinton.light.activities.MainActivity;
import com.example.clinton.light.database.NewsContract;
import com.example.clinton.light.fetchData.FetchNewsIntentService;


public class SportFrag extends TabsSuperClass
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int POSITION = 4;

    public SportFrag() {
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(POSITION, null, this);
    }

    public void CallFetchData() {
        if (CheckConnectionState()) {
            if (!MainActivity.SPORT_STATE) {
                MainActivity.SPORT_STATE = true;
                _isLoading = true;
                Intent intent = new Intent(getContext(), FetchNewsIntentService.class);
                intent.putExtra(FetchNewsIntentService.SERVICE_DATA, POSITION);
                intent.putExtra(FetchNewsIntentService.SERVICE_STATE, CLEAR);
                progressBar.setVisibility(View.VISIBLE);
                getContext().startService(intent);
            }
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "You are offline", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), NewsContract.CONTENT_URI_SPORT, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null)
        {
            ResetLayout(null);
            return;
        }
        if (data.getCount() == 0) {
            CallFetchData();
        } else {
            MainActivity.LIFESTYLE_STATE = false;
            ResetLayout(data);
            WaitSomeTime();

        }
    }


    private void ResetLayout(Cursor data)
    {
        progressBar.setVisibility(View.GONE);
        mButton.setVisibility(View.GONE);
        //recyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);

        _isLoading = false;
        if (data != null)
            mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
