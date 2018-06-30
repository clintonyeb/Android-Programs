package com.example.clinton.companion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.clinton.companion.R;
import com.example.clinton.companion.design.MainWindowInterface;
import com.example.clinton.companion.dictionaryFragments.AllDictionaryFragment;
import com.example.clinton.companion.dictionaryFragments.FavoriteWordFrag;
import com.example.clinton.companion.dictionaryFragments.MenuFragment;
import com.example.clinton.companion.dictionaryFragments.RandomWord;
import com.example.clinton.companion.dictionaryFragments.RecentFrag;
import com.example.clinton.companion.dictionaryFragments.RelatedFragment;

public class DictionaryMenu extends AppCompatActivity
implements MainWindowInterface,AllDictionaryFragment.OnMainDictionaryListener ,
        MenuFragment.OnMenuDictionaryListener{

    Fragment currentFragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_menu);
        initializeViews();
        setEvents();
        initializeMembers();
    }

    @Override
    public void initializeViews() {
        currentFragment = new MenuFragment();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitializeFragment();
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void initializeMembers() {

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onMenuItemClicked(int position) {
        switch (position)
        {
            case 0:
                Intent  intent = new Intent(this, TodayWordActivity.class);
                startActivity(intent);
                return;
            case 1:
                currentFragment = new RelatedFragment();
                break;
            case 2:
                currentFragment = new RandomWord();
                break;
            case 3:
                onBackPressed();
                return;
            case 4:
                currentFragment = new FavoriteWordFrag();
                break;
            case 5:
                currentFragment = new RecentFrag();
                break;
            default:
                throw new IllegalArgumentException("Unknown id " + position);

        }
        InitializeFragment();
    }

    public void InitializeFragment()
    {
        if (currentFragment != null)
        {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (currentFragment instanceof MenuFragment)
                transaction.replace(R.id.MainFrameLayout, currentFragment)
                        .commit();
            else
                transaction.replace(R.id.MainFrameLayout, currentFragment)
                        .addToBackStack(null)
                        .commit();
        }
    }

    public Toolbar getToolbar()
    {
        return this.toolbar;
    }

    @Override
    public void onMoreMenuClicked() {

    }

    @Override
    public void onItemClicked(int item) {

    }
}
