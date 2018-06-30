package com.example.clinton.companion.MainFragments;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.clinton.companion.R;
import com.example.clinton.companion.activities.MainActivity;
import com.example.clinton.companion.dictionaryFragments.AllDictionaryFragment;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

public class DictionaryFragment extends AllDictionaryFragment {

    public DictionaryFragment() {}

    public static DictionaryFragment newInstance(String param1, String param2) {
        DictionaryFragment fragment = new DictionaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Fragment_ID = FRAGMENT_ID.MAIN_DICTIONARY_ID;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchview_menu, menu);
        inflater.inflate(R.menu.dict_menu, menu);
        CreateSearchView(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.more_dict:
                listener.onMoreMenuClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    private void CreateSearchView(Menu menu)
    {
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty())
                {
                    CallToFetchData(query);
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
}
