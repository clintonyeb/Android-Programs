package com.example.clinton.companion.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.clinton.companion.R;
import com.example.clinton.companion.tabsFragments.AllTabsSuper;

public class NewsFavorites extends AppCompatActivity implements
        AllTabsSuper.OnTabsFragmentInteractionListener{

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_favorites);
        InitializeViews();
        SetEvents();
        InitializeMembers();
    }

    private void InitializeMembers() {

    }

    private void SetEvents() {

    }

    private void InitializeViews() {
        currentFragment = new NewsFavoritesFragment();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitializeFragment();
    }

    private void InitializeFragment() {
        if (currentFragment != null)
        {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.MainFrameLayout, currentFragment)
                        .commit();

        }
    }

    @Override
    public void onFragmentInteraction(boolean state) {

    }

    @Override
    public void onItemClicked(int id) {

    }
}
