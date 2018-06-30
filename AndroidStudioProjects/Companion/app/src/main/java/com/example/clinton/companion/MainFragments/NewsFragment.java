package com.example.clinton.companion.MainFragments;

import android.content.Context;
import android.content.Intent;
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
import com.example.clinton.companion.activities.NewsFavorites;
import com.example.clinton.companion.tabsFragments.CultureFrag;
import com.example.clinton.companion.tabsFragments.LifeStyleFrag;
import com.example.clinton.companion.tabsFragments.ScienceFrag;
import com.example.clinton.companion.tabsFragments.SportFrag;
import com.example.clinton.companion.tabsFragments.TabsAdapter;
import com.example.clinton.companion.tabsFragments.WorldFrag;

public class NewsFragment extends Fragment {

    private static final String NewsParam1 = "param1";
    private static final String NewsParam2= "param2";

    public LifeStyleFrag lifeStyleFrag;
    public CultureFrag cultureFrag;
    public ScienceFrag scienceFrag;
    public SportFrag sportFrag;
    public WorldFrag worldFrag;

    TabLayout tabs;
    ViewPager viewPager;
    View rootView;
    SearchView searchView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnNewsFragmentInteractionListener mListener;

    public NewsFragment() {}

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(NewsParam1, param1);
        args.putString(NewsParam2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(NewsParam1);
            mParam2 = getArguments().getString(NewsParam2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.fav:
                Intent intent = new Intent(getContext(), NewsFavorites.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.searchview_menu, menu);
        inflater.inflate(R.menu.news_menu, menu);
        //CreateSearchView(menu);
    }

    private void CreateSearchView(Menu menu)
    {
        MenuItem item = menu.findItem(R.id.search);
        searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mListener.SearchSubmitted(query, false);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.tabs_layout, container, false);
        InitializeMembers();
        CreateTabs();
        return rootView;
    }

    private void CreateTabs() {
        final TabLayout.Tab LifeStyle = tabs.newTab();
        final TabLayout.Tab Science = tabs.newTab();
        final TabLayout.Tab Sport = tabs.newTab();
        final TabLayout.Tab Culture = tabs.newTab();
        final TabLayout.Tab World = tabs.newTab();

        LifeStyle.setText(R.string.life);
        Science.setText(R.string.science);
        Sport.setText(R.string.sport);
        Culture.setText(R.string.culture);
        World.setText(R.string.world);

        tabs.addTab(LifeStyle, 0);
        tabs.addTab(Science, 1);
        tabs.addTab(Sport ,2);
        tabs.addTab(Culture, 3);
        tabs.addTab(World, 4);

        SetTabsFeatures();
    }

    private void SetTabsFeatures()
    {
        tabs.setTabTextColors(ContextCompat.getColorStateList(getActivity(), R.drawable.tab_selected));
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.icons));

        TabsAdapter tabsAdapter = new TabsAdapter(getFragmentManager(), tabs.getTabCount(), this);

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

        lifeStyleFrag = new LifeStyleFrag();
        cultureFrag = new CultureFrag();
        scienceFrag = new ScienceFrag();
        sportFrag = new SportFrag();
        worldFrag = new WorldFrag();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsFragmentInteractionListener) {
            mListener = (OnNewsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTabsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnNewsFragmentInteractionListener {
        void SearchSubmitted(String query, boolean searchFragmentShowing);
    }
}
