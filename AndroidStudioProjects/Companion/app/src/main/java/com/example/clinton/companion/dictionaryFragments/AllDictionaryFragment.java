package com.example.clinton.companion.dictionaryFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.clinton.companion.R;
import com.example.clinton.companion.design.MainWindowInterface;
import com.example.clinton.companion.dictionary.adapters.DictionaryAdapter;
import com.example.clinton.companion.dictionary.facades.DictionaryHelper;
import com.example.clinton.companion.dictionary.workers.FetchJSONArrayRequest;
import com.example.clinton.companion.dictionary.workers.FetchJSONObjectRequest;
import com.example.clinton.companion.news.workers.HandleNewsData;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.API_KEYS;
import com.example.clinton.companion.utilities.FRAGMENT_ID;
import com.example.clinton.companion.utilities.HelperMethods;
import com.example.clinton.companion.utilities.QuickstartPreferences;

import java.util.Random;

public class AllDictionaryFragment extends Fragment
        implements MainWindowInterface,
        LoaderManager.LoaderCallbacks<Cursor> {

    protected static final String ARG_PARAM1 = "param1";
    protected static final String ARG_PARAM2 = "param2";

    protected DictionaryAdapter adapter;
    protected RecyclerView recyclerView;
    protected View rootView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected boolean imBusy;
    protected FRAGMENT_ID Fragment_ID = null;
    protected OnMainDictionaryListener listener;
    LinearLayoutManager linear;
    FetchJSONObjectRequest objectRequest;
    FetchJSONArrayRequest arrayRequest;
    private String mParam1;
    private String mParam2;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ProgressBar progressBar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new DictionaryAdapter(this.getContext(), null , Fragment_ID);
        recyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(Fragment_ID.ordinal(), null, this);
    }

    /*protected boolean CheckFirstTime()
    {
        if (HelperMethods.CheckAppStart(String.valueOf(Fragment_ID), getContext()))
        {
            switch (Fragment_ID)
            {
                case MAIN_DICTIONARY_ID:
                case RANDOM_WORD_ID:
                    CallToFetchData("");
                    break;
                case RELATED_ID:
                    String url = "https://api.datamuse.com/words?ml=light&max=20";
                    CallToFetchData(url);
            }
            return true;
        }
        return false;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.SERVICE_COMPLETE));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dictionary, container, false);
        initializeViews();
        setEvents();
        initializeMembers();
        return rootView;
    }

    @Override
    public void initializeViews() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.lRecyclerView);
        recyclerView.setHasFixedSize(true);
        linear  = new LinearLayoutManager(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.lSwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorPrimary);
        recyclerView.setLayoutManager(linear);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
    }

    @Override
    public void setEvents() {
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void initializeMembers() {
        objectRequest = new FetchJSONObjectRequest();
        arrayRequest = new FetchJSONArrayRequest();
        imBusy = false;
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                NewsResults newsResults = (NewsResults) intent.getSerializableExtra(HandleNewsData.SERVICE_RESULT);
                setmRegistrationBroadcastReceiver(newsResults);
                resetProgress();
            }
        };

    }

    public void setmRegistrationBroadcastReceiver(NewsResults newsResults)
    {
        String text;
        Snackbar snackbar = null;
        switch (newsResults)
        {
            case Timeout:
                text =  "Network too slow";
                snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                snackbar.setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    }
                });
                break;
            case Network:
                text =  "Network too slow";
                snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                snackbar.setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    }
                });
                break;
            case NoConnection:
                text = "Not connected";
                snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                snackbar.setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    }
                });
                break;
            case Success:
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String text = adapter.getItemCount() + " results";
                        Snackbar.make(rootView, text, Snackbar.LENGTH_LONG).show();
                    }
                }, 100);

                break;
            case AddedToFavorites:
                text = "Added to Favorites";
                snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                snackbar.setAction("Check", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClicked(2);
                    }
                });
                break;
            case RemovedFromFavorites:
                text = "Removed from Favorites";
                snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                snackbar.setAction("Check", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClicked(2);
                    }
                });
                break;
            default:
                text = "Something went wrong";
                snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                break;

        }
        if (snackbar != null) {
            snackbar.show();
        }
    }


    @Override
    public void callToFetchData() {
        CallToFetchData("");
    }

    public void CallToFetchData(String userWord)
    {
        if (imBusy) return;
        showProgress();
        switch (Fragment_ID)
        {
            case MAIN_DICTIONARY_ID:
                objectRequest.MakeRequest(this.getContext(),
                        MakeAddressFromWord(HelperMethods.MakeWord(userWord)), Fragment_ID);
                break;
            case RANDOM_WORD_ID:
                objectRequest.MakeRequest(this.getContext(), getRandomWord(), Fragment_ID);
                break;
            case RELATED_ID:
                arrayRequest.MakeRequest(this.getContext(), userWord, Fragment_ID);
                break;
        }
    }

    private String getRandomWord()
    {
        String uri = "http://api.pearson.com/v2/dictionaries/lasde/entries?offset=%s&" +
                "limit=10&apikey=" + API_KEYS.PEARSON_API_2;
        return  String.format(uri, new Random().nextInt(21156));
    }

    @Override
    public void makeBusy(boolean state) {

    }

    @Override
    public void showProgress() {
        imBusy = true;
    }

    @Override
    public void resetProgress() {
        imBusy = false;
    }

    private String MakeAddressFromWord(String userWord)
    {
        String baseAdd = "http://api.pearson.com/v2/dictionaries/lasde/" +
                "entries?headword=%s&limit=10&apikey=" + API_KEYS.PEARSON_API_1;
        return String.format(baseAdd ,userWord);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), DictionaryHelper.GetDictionaryUri(Fragment_ID), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader == null) return;
        adapter.swapCursor(data);
        if (adapter.getItemCount() < 1)
        {
            switch (Fragment_ID)
            {
                case MAIN_DICTIONARY_ID:
                case RANDOM_WORD_ID:
                    CallToFetchData("");
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                /*case RELATED_ID:
                    String url = "https://api.datamuse.com/words?ml=light&max=20";
                    CallToFetchData(url);
                    progressBar.setVisibility(View.VISIBLE);
                    break;*/
            }

        }
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainDictionaryListener) {
            listener = (OnMainDictionaryListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMainDictionaryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                break;
        }
        return true;
    }

    public interface OnMainDictionaryListener {
        void onMoreMenuClicked();
        void onItemClicked(int item);
    }
}
