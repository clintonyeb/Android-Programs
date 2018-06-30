package com.example.clinton.light.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.clinton.light.R;
import com.example.clinton.light.database.DictionaryContract;
import com.example.clinton.light.random_word.RandomAdapter;

public class AllRecentActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    public RecyclerView mRecyclerView;
    public RandomAdapter  mAdapter;
    public static int position = -1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recent);
        MainActivity.SetStrictMode();
        Intent intent = getIntent();
        position = intent.getIntExtra("POSITION", -1);
        switch (position)
        {
            case 0:
                getSupportLoaderManager().initLoader(10, null, this);
                this.setTitle("Recent Searches");
                mAdapter = new RandomAdapter(getApplicationContext(), null, 3);
                break;
            case 1:
                getSupportLoaderManager().initLoader(12, null, this);
                mAdapter = new RandomAdapter(getApplicationContext(), null, 2);
                this.setTitle("Favorites");
                break;
        }
        mRecyclerView = (RecyclerView)findViewById(R.id.dict_details_recycler_view);
        mAdapter = new RandomAdapter(getApplicationContext(), null, 2);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(manager);
        /*mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getApplicationContext()
        ));*/

       mRecyclerView.setAdapter(mAdapter);
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


    @Override
    public Loader<Cursor> onCreateLoader (int id, Bundle args) {
        switch (position)
        {
            case 0:
                return new CursorLoader(getApplicationContext(), DictionaryContract.CONTENT_URI_RECENT, null, null,
                        null, "_id DESC" );
            case 1:
                return new CursorLoader(getApplicationContext(), DictionaryContract
                        .CONTENT_URI_FAVORITES, null, null, null,"_id DESC" );
        }
        return null;
    }

    @Override
    public void onLoadFinished (Loader<Cursor> loader, Cursor data) {
       mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset (Loader<Cursor> loader) {
       mAdapter.swapCursor(null);
    }
}
