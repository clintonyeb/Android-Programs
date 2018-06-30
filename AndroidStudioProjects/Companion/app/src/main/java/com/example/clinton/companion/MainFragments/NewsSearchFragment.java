package com.example.clinton.companion.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.clinton.companion.R;
import com.example.clinton.companion.activities.MainActivity;
import com.example.clinton.companion.tabsFragments.SearchAdapter;

public class NewsSearchFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Fragment currentFragment;
    View rootView;
    TabLayout tabs;
    ViewPager viewPager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public NewsSearchFragment() {
        // Required empty public constructor
    }


    public static NewsSearchFragment newInstance(String query, String param2) {
        NewsSearchFragment fragment = new NewsSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, query);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchview_menu, menu);
        CreateSearchView(menu);
    }

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
                mListener.SearchSubmitted(query, true);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =   inflater.inflate(R.layout.tabs_layout, container, false);
        InitializeMembers();
        CreateTabs();
        return rootView;
    }

    private void CreateTabs() {
        final TabLayout.Tab Search = tabs.newTab();
        final TabLayout.Tab Favorites = tabs.newTab();

        Search.setText("Search");
        Favorites.setText("Favorites");

        tabs.addTab(Search, 0);
        tabs.addTab(Favorites, 1);

        SetTabsFeatures();
    }

    private void SetTabsFeatures()
    {
        tabs.setTabTextColors(ContextCompat.getColorStateList(getActivity(), R.drawable.tab_selected));
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.icons));

        SearchAdapter tabsAdapter = new SearchAdapter(getFragmentManager(), tabs.getTabCount(), this);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(tabsAdapter);
    }


    private void InitializeMembers() {
        tabs = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        void SearchSubmitted(String query,boolean searchFragmentShowing);
    }
}
