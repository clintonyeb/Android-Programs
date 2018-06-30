package com.example.clinton.light.menuFragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clinton.light.R;
import com.example.clinton.light.tabs.TabsAdapter;

public class TodayNewsFragment extends Fragment {
    ViewPager viewPager;



    public TodayNewsFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_today_news, container, false);
        // Inflate the layout for this fragment
        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);



        final TabLayout.Tab World = tabLayout.newTab();
        final TabLayout.Tab Science = tabLayout.newTab();
        final TabLayout.Tab Sport = tabLayout.newTab();
        final TabLayout.Tab Culture = tabLayout.newTab();
        final TabLayout.Tab LifeStyle = tabLayout.newTab();

        World.setText("Life and Style");
        Science.setText("Technology");
        Sport.setText("Sports");
        Culture.setText("Culture");
        LifeStyle.setText("World");

        //News1.setIcon(R.drawable.news);
        tabLayout.addTab(World, 0);
        tabLayout.addTab(Science, 1);
        tabLayout.addTab(Sport, 2);
        tabLayout.addTab(Culture, 3);
        tabLayout.addTab(LifeStyle, 4);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getActivity(), R.drawable.tab_selected));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.icons));

        TabsAdapter tabsAdapter = new TabsAdapter(getFragmentManager(), tabLayout.getTabCount());

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
        return rootView;
    }
}
