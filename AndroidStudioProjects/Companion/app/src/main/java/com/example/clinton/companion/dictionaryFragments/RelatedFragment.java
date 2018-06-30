package com.example.clinton.companion.dictionaryFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.clinton.companion.R;
import com.example.clinton.companion.activities.DictionaryMenu;
import com.example.clinton.companion.activities.MainActivity;
import com.example.clinton.companion.dictionary.facades.DictionaryHelper;
import com.example.clinton.companion.dictionary.adapters.RelatedAdapter;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.FRAGMENT_ID;
import com.example.clinton.companion.utilities.HelperMethods;
import com.example.clinton.companion.utilities.YourObjectSpinnerAdapter;

import java.util.Arrays;
import java.util.List;


public class RelatedFragment extends AllDictionaryFragment {

    private final String SPINNER_KEY = "spinner_key";
    Toolbar toolbar;
    View spinnerContainer;
    RelatedAdapter adapter;
    SharedPreferences preferences;
    Spinner spinner;

    public RelatedFragment() {}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.searchview_menu, menu);
        CreateSearchView(menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Fragment_ID = FRAGMENT_ID.RELATED_ID;
        super.onActivityCreated(savedInstanceState);
        adapter = new RelatedAdapter(this.getContext(), null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initializeViews() {
        super.initializeViews();
        super.adapter = null;
        toolbar =  ((DictionaryMenu)this.getActivity()).getToolbar();
        spinnerContainer = LayoutInflater.from(getContext()).inflate(R.layout.toolbar_spinner,
                toolbar, false);
        SetSpinner();

    }

    @Override
    public void initializeMembers() {
        preferences = getContext().getSharedPreferences
                (MainActivity.PREF_FILE, Context.MODE_PRIVATE);
        spinner.setSelection(getSpinnerPosition());
        super.initializeMembers();
    }

    private String getRelatedWordUri(String query)
    {
        return GetUri(HelperMethods.MakeWord(query));
    }

    private String GetUri(String word)
    {
        String param1 = "";
        String url = null;

        switch (getSpinnerPosition())
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

    public int getSpinnerPosition()
    {
        return preferences.getInt(SPINNER_KEY, 0);

    }

    private void SetSpinner()
    {

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toolbar.addView(spinnerContainer, lp);

        YourObjectSpinnerAdapter spinnerAdapter = new YourObjectSpinnerAdapter(getContext());
        spinnerAdapter.addItems(getMyObjectSpinnerData());

        spinner = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferences.edit().putInt(SPINNER_KEY, position).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RemoveSpinner();
    }

    private void RemoveSpinner()
    {
        toolbar.removeView(spinnerContainer);
    }

    private List<String> getMyObjectSpinnerData()
    {
        String[] spinnerData = getContext().getResources().getStringArray(R.array.spinner_data);
        return Arrays.asList(spinnerData);
    }

    @SuppressWarnings("ConstantConditions")
    private void CreateSearchView(Menu menu)
    {
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((DictionaryMenu) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty())
                {
                    CallToFetchData(getRelatedWordUri(query));
                }
                return  true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener );
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
            Log.i("RELATED FRAGMENT", "here");
            String url = "https://api.datamuse.com/words?ml=light&max=20";
            CallToFetchData(url);
            progressBar.setVisibility(View.VISIBLE);
        }
       else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void setmRegistrationBroadcastReceiver(NewsResults newsResults) {
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
}
