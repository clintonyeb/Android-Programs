package com.example.clinton.companion.dictionaryFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.clinton.companion.R;
import com.example.clinton.companion.design.MainWindowInterface;
import com.example.clinton.companion.dictionary.adapters.MenuAdapter;


public class MenuFragment extends Fragment
        implements MainWindowInterface {

    public ListView listView;
    String[] array;
    int[] imageId;
    MenuAdapter adapter;
    View  rootView;
    private OnMenuDictionaryListener listener;

    public MenuFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        initializeViews();
        setEvents();
        initializeMembers();
        return rootView;
    }


    @Override
    public void initializeViews() {
        listView = (ListView)rootView.findViewById(R.id.words_listview);
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void initializeMembers() {
        array = new String[]
                {
                        "Word-of-the-Day",
                        "Related Words",
                        "Random Words",
                        "Search Dictionary",
                        "Your Favorites",
                        "Your Recent Searches"
                };

        imageId = new int[]{
                R.drawable.icon_dict_menu,
        };

        adapter = new MenuAdapter(this, getContext(), array, imageId);
        listView.setAdapter(adapter);
    }



    @Override
    public void callToFetchData() {

    }

    @Override
    public void makeBusy(boolean state) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void resetProgress() {

    }
    public void ItemSelected(int position)
    {
       if (listener != null)
       {
           listener.onMenuItemClicked(position);
       }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuDictionaryListener) {
            listener = (OnMenuDictionaryListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMenuDictionaryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnMenuDictionaryListener {
        void onMenuItemClicked(int position);
    }
}
