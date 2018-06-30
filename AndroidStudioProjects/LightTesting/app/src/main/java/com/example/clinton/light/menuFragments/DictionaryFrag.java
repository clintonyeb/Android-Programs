package com.example.clinton.light.menuFragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinton.light.R;
import com.example.clinton.light.activities.MainActivity;
import com.example.clinton.light.activities.WordsMenu;
import com.example.clinton.light.database.DictionaryContract;
import com.example.clinton.light.dictionary_main.FetchWordRequest;
import com.example.clinton.light.random_word.RandomAdapter;
import com.example.clinton.light.utilities.DictDialog;

public class DictionaryFrag extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    public EditText editText;
    FetchWordRequest fetchWordRequest;
    ImageButton imageButton;
    ProgressBar progressBar;
    RecyclerView mRecyclerView;
    public RandomAdapter mAdapter;
    DictionaryReadyToLoadData mListener;

    public DictionaryFrag () {}

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(30, null, this);
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_dictionary, container, false);

        editText = (EditText)rootView.findViewById(R.id.editText);
        imageButton = (ImageButton)rootView.findViewById(R.id.words_menu);
        fetchWordRequest  = new FetchWordRequest();
        mRecyclerView = (RecyclerView)rootView. findViewById(R.id.dict_recycler_view);
        mAdapter = new RandomAdapter(getContext(), null, 0);
        mRecyclerView.setHasFixedSize(true);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        setHasOptionsMenu(true);
       /* mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getContext()
        ));*/
        mRecyclerView.setAdapter(mAdapter);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WordsMenu.class);
                startActivity(intent);
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String userWord = editText.getText().toString();
                    if (!userWord.isEmpty()) {
                        CallForResult(userWord);

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
                            CallForResult(userWord);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ShowDictDialog();
                break;
        }
        return true;
    }
    private void ShowDictDialog() {
        DialogFragment dialog = new DictDialog();
        dialog.show(getActivity().getFragmentManager(), "DictDialog");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
    }

    public void CallForResult(String userWord)
    {
        if (CheckConnectionState())
        {
            editText.setText(userWord);
            editText.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            MainActivity.DICTIONARY_STATE = true;
            fetchWordRequest.MakeRequest(getContext(), this, MakeAddress(MakeWord(userWord)));
        }
        else
        {
            Toast.makeText(getContext(), "You are offline", Toast.LENGTH_LONG).show();
        }

    }

    protected   boolean CheckConnectionState()
    {
        ConnectivityManager manager = (ConnectivityManager)getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    private static String MakeWord(String word)
    {
        word = word.trim();
        word = word.toLowerCase();
        word = word.trim();
        word = word.replaceAll("\\s+", " ");
        word = word.replace(" ", "+");
        return word;
    }
    private String MakeAddress(String userWord)
    {
        String baseAdd = "http://api.pearson.com/v2/dictionaries/lasde/" +
                "entries?headword=%s&limit=10&apikey=zoAgBxGASG4XTHDtz3OjxlqwNAeXKt03";
        return String.format(baseAdd ,userWord);
    }

    @Override
    public Loader<Cursor> onCreateLoader (int id, Bundle args) {
        return new CursorLoader(getContext(), DictionaryContract.CONTENT_URI_DICTIONARY,
                null, null, null, null );
    }

    @Override
    public void onLoadFinished (Loader<Cursor> loader, Cursor data) {
        if (data == null)
        {
            ResetLayout(null);
            editText.setText(String.format("No results found for %s", editText.getText().toString()));
            return;
        }


        if ( data.getCount() == 0)
            CallForResult("light");
        else
        {
            if (!MainActivity.DICTIONARY_STATE)
            {
             ResetLayout(data);
            }
            MainActivity.DICTIONARY_STATE = false;
        }
    }
    private void ResetLayout(Cursor data)
    {
        editText.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        if (data != null)
            mAdapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset (Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public interface DictionaryReadyToLoadData
    {
        void BeginDictionaryLoadData();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            mListener = (DictionaryReadyToLoadData) activity;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()+
                    "must implement interface");
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mListener.BeginDictionaryLoadData();
    }

}

