package com.example.clinton.light.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.clinton.light.R;
import com.example.clinton.light.database.DictionaryContract;
import com.example.clinton.light.random_word.FetchRandomWord;
import com.example.clinton.light.random_word.RandomAdapter;

import java.util.Random;

public class 	RandomWord extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    public RecyclerView mRecyclerView;
    public RandomAdapter mAdapter;
	private MenuItem m;
	private boolean REFRESHING_STATE = false;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.refresh_menu, menu);
		m = menu.findItem(R.id.action_refreshing);
		return true;

	}

	@Override
	protected void onStart() {
		super.onStart();
		REFRESHING_STATE = false;
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_word);
		MainActivity.SetStrictMode();
        getSupportLoaderManager().initLoader(29, null, this);
        this.setTitle("Random Words");

        mRecyclerView = (RecyclerView)findViewById(R.id.random_recycler_view);
        mAdapter = new RandomAdapter(this, null, 1);
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
	        case R.id.action_refreshing:
		        CallForData();
	            break;
        }
        return true;
    }

	public void resetUpdating()
	{
		if(m != null)
		{
			if(m.getActionView() != null)
			{
				m.getActionView().clearAnimation();
				m.setActionView(null);
			}

		}
	}
	protected void RefreshState()
	{
		if(m != null)
		{
			LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ImageView iv = (ImageView)inflater.inflate(R.layout.progress_ring, null);
			Animation rotate = AnimationUtils.loadAnimation(this, R.anim.roate_refresh);
			rotate.setRepeatCount(Animation.INFINITE);
			iv.startAnimation(rotate);
			m.setActionView(iv);
		}
	}
    @Override
    public Loader<Cursor> onCreateLoader (int id, Bundle args) {
        return new CursorLoader(getApplicationContext(), DictionaryContract.CONTENT_URI_RANDOM, null, null,
                null, null);
    }

    @Override
    public void onLoadFinished (Loader<Cursor> loader, Cursor data) {
	    if (data.getCount() == 0)
		    CallForData();
	    else
	    {
		    REFRESHING_STATE = false;
		    mAdapter.swapCursor(data);
		    resetUpdating();
	    }

    }

    @Override
    public void onLoaderReset (Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    private void CallForData()
    {
	    if (!REFRESHING_STATE)
	    {
		    RefreshState();
		    REFRESHING_STATE = true;
		    String uri = "http://api.pearson.com/v2/dictionaries/lasde/entries?offset=%s&limit=10&apikey=fawyvCq8NnjsA2Mc1EWAqkIGdwv56WlE";
		    String address = String.format(uri, new Random().nextInt(21156));
		    FetchRandomWord randomWord = new FetchRandomWord();
		    randomWord.MakeRequest(this, this, address);
	    }

    }
}

