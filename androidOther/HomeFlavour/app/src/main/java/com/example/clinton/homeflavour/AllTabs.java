package com.example.clinton.homeflavour;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AllTabs extends Fragment implements ActivityInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    HomeFragment homeFragment;
    TabLayout tabLayout;
    ViewPager viewPager;
    View rootView;


    public AllTabs() {}

    public static AllTabs newInstance(String param1, String param2) {
        AllTabs fragment = new AllTabs();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all_tabs, container, false);
        initializeViews();
        initializeMembers();
        setEvents();
        createTabs();
        return rootView;
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

    @Override
    public void initializeViews() {
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void initializeMembers() {
        homeFragment = new HomeFragment();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void createTabs() {
        final TabLayout.Tab HomeTab = tabLayout.newTab();
        final TabLayout.Tab MenuTab = tabLayout.newTab();
        final TabLayout.Tab OrderOnlineTab = tabLayout.newTab();
        final TabLayout.Tab AboutUsTab = tabLayout.newTab();
        final TabLayout.Tab CherishFoodTab = tabLayout.newTab();
        final TabLayout.Tab ContactTab = tabLayout.newTab();

        HomeTab.setText(R.string.home);
        MenuTab.setText("Menu");
        OrderOnlineTab.setText("Order Online");
        AboutUsTab.setText("About Us");
        CherishFoodTab.setText("Lets' Cherish Food");
        ContactTab.setText("Contact Us");


        tabLayout.addTab(HomeTab, 0);
        tabLayout.addTab(MenuTab, 1);
        tabLayout.addTab(OrderOnlineTab ,2);
        tabLayout.addTab(AboutUsTab, 3);
        tabLayout.addTab(CherishFoodTab, 4);
        tabLayout.addTab(ContactTab, 5);

        setTabsFeatures();
    }

    private void setTabsFeatures()
    {
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.drawable.tab_selected));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.icons));

        TabsAdapter tabsAdapter = new TabsAdapter(getFragmentManager(), tabLayout.getTabCount(), this);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
}
