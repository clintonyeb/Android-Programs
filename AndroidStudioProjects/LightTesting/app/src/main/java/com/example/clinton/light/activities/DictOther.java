package com.example.clinton.light.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clinton.light.R;
import com.example.clinton.light.database.DictionaryContract;
import com.example.clinton.light.dict_other.DictOtherAdapter;
import com.example.clinton.light.dict_other.FetchDataDict;
import com.example.clinton.light.utilities.SpinnerAdapter;

public class DictOther extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{


    public RecyclerView mRecyclerView;
    public DictOtherAdapter mAdapter;
    public EditText editText;
    FetchDataDict fetchDataDict;
    ProgressBar progressBar;
    TextView wordNumber;
	int spinner_pos = 0;

	private void SetTitle()
	{
        String[] meanings = getResources().getStringArray(R.array.spinner_explain);
        String[] types = getResources().getStringArray(R.array.spinner_other);
        this.setTitle(types[spinner_pos]);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict_other);
        MainActivity.SetStrictMode();
	    Spinner spinner = null;

        String[] types = getResources().getStringArray(R.array.spinner_other);
         spinner = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(DictOther.this, R.layout.spinner_item, types);
	    if (spinner != null) {
		    spinner.setAdapter(spinnerAdapter);
		    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				    spinner_pos = position;
				    SetTitle();
			    }

			    @Override
			    public void onNothingSelected(AdapterView<?> parent) {

			    }
		    });


	    fetchDataDict = new FetchDataDict();
        mRecyclerView = (RecyclerView)findViewById(R.id.dict_other_recycler_view);
        mAdapter = new DictOtherAdapter(DictOther.this, null);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(manager);
            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            wordNumber = (TextView)findViewById(R.id.word_numb);

        getSupportLoaderManager().initLoader(20, null, this);

        mRecyclerView.setAdapter(mAdapter);
        editText = (EditText)findViewById(R.id.editText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String userWord = editText.getText().toString();
                    if (!userWord.isEmpty()) {
                        CallForResult(DictOther.this, makeWord(userWord));
                    }
                    return true;
                }
                return false;
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if ((event.getAction() == MotionEvent.ACTION_UP)) {
                    if (event.getRawX() >= editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {

                        String userWord = editText.getText().toString();
                        if (!userWord.isEmpty()) {
                            CallForResult(DictOther.this, makeWord(userWord));
                        }


                        return true;
                    }
                }
                return false;
            }
        });
    }
        SetTitle();
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
    private void CallForResult(DictOther other, String userWord)
    {
        editText.setEnabled(false);
        mRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        fetchDataDict.MakeRequest(this, DictOther.this, GetUri(userWord));
    }

    private String GetUri(String word)
    {
        String param1 = "";
        String url = null;

        switch (spinner_pos)
        {
            case 0:
                param1 = "ml";
                break;
            case 1:
                param1 = "sl";
                break;
            case 2:
                param1 = "sp";
                break;
	        case 3:
		        param1 = "rel_syn";
		        break;
	        case 4:
		        param1 = "rel_ant";
		        break;
	        case 5:
		        param1 = "rel_spc";
		        break;
	        case 6:
		        param1 = "rel_gen";
		        break;
	        case 7:
				param1 = "rel_com";
		        break;
	        case 8:
		        param1 = "rel_par";
		        break;
	        case 9:
		        param1 = "rel_rhy";
		        break;
	        case 10:
		        param1 = "rel_nry";
		        break;
	        case 11:
		        param1 = "rel_hom";
		        break;
	        default:
		        break;

        }
        final String baseUri = "https://api.datamuse.com/words?";
        url = baseUri + param1 + "=" +word + "&max=20";
        return url;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(), DictionaryContract.CONTENT_URI_OTHER, null, null,
                null, null );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        editText.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        if (data != null)
        {
            wordNumber.setText(Integer.toString(data.getCount()));
            mAdapter.swapCursor(data);
        }
    }

    private static String makeWord(String word)
    {
        word = word.trim();
        word = word.toLowerCase();
	    word = word.trim();
	    word = word.replaceAll("\\s+", " ");
        word = word.replace(" ", "+");
        return word;
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
