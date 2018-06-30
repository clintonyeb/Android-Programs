package com.example.clinton.companion.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.clinton.companion.MainFragments.DictionaryFragment;
import com.example.clinton.companion.MainFragments.HomeFragment;
import com.example.clinton.companion.MainFragments.NewsFragment;
import com.example.clinton.companion.MainFragments.RemindMeFragment;
import com.example.clinton.companion.R;
import com.example.clinton.companion.design.MainActivityInterface;
import com.example.clinton.companion.tabsFragments.SearchFrag;
import com.example.clinton.companion.todayword.TodayWordReceiver;
import com.example.clinton.companion.todayword.WordBootReceiver;
import com.example.clinton.companion.utilities.FRAGMENT_ID;
import com.example.clinton.companion.utilities.HelperMethods;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainActivityInterface,
HomeFragment.OnFragmentInteractionListener{

    public static final String PREF_FILE = "com.example.clinton.companion.sharedpreference";
    private static final int TIME_INTERVAL = 3000;
    private static boolean DEVELOPER_MODE = true;
    private final FRAGMENT_ID fragmentId = FRAGMENT_ID.MAIN_ACTIVITY_ID;
    Toolbar toolbar;
    /*FloatingActionButton fab;*/
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    SharedPreferences preferences;
    Fragment currentFragment = null;
    Fragment previousFragment = null;
    private long mBackPressed;
    private String SearchData = "";

    public static void SetStrictMode() {
        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetStrictMode();
        initializeViews();
        setEvents();
        initializeMembers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (HelperMethods.CheckAppStart(String.valueOf(fragmentId), this))
        {
            drawer.openDrawer(GravityCompat.START);
            preferences =  getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);
            preferences.edit().putBoolean(String.valueOf(fragmentId), false).apply();
            onNavigationItemSelected(navigationView != null ? navigationView.getMenu().findItem(R.id.main_news) : null);
        }
        Intent intent = new Intent(this, TodayWordReceiver.class);
        sendBroadcast(intent);
        WordBootReceiver.SetAlarm(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
                return;
            }
        }
        if (currentFragment instanceof HomeFragment) {
            ExitActivity();
        } else {
            onNavigationItemSelected(navigationView != null ? navigationView.getMenu().findItem(R.id.home_page) : null);
        }
    }

    private void ExitActivity() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        previousFragment = currentFragment;

        switch (id) {
            case R.id.home_page:
                currentFragment = HomeFragment.newInstance(null, null);
                this.setTitle(this.getString(R.string.app_name));
                break;
            case R.id.news_favorites:
                intent = new Intent(this, NewsFavorites.class);
            case R.id.main_news:
                currentFragment = NewsFragment.newInstance(null, null);
                this.setTitle(this.getString(R.string.str_news));
                break;
            case R.id.all_dict:
                intent = new Intent(this, DictionaryMenu.class);
            case R.id.main_dict:
                currentFragment = DictionaryFragment.newInstance(null, null);
                this.setTitle(this.getString(R.string.search_dictionary));
                break;
            case R.id.remindme:
                currentFragment = RemindMeFragment.newInstance(null, null);
                this.setTitle(this.getString(R.string.str_notify));
                break;
        }

        setMenuItemChecked(item);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (intent != null)
            startActivity(intent);

        InitializeFragment();
        return true;
    }

    private void InitializeFragment()
    {
        if (currentFragment == null) return;
        if (previousFragment != null && previousFragment.getClass().equals(currentFragment.getClass())) return;;
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.MainFrameLayout, currentFragment).commit();
    }

    private void setMenuItemChecked(MenuItem item)
    {
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        navigationView.getMenu().findItem(item.getItemId()).setChecked(true);
    }

    @Override
    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*fab = (FloatingActionButton) findViewById(R.id.fab);*/
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);

    }

    @Override
    public void setEvents() {
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void initializeMembers() {
        onNavigationItemSelected(navigationView != null ? navigationView.getMenu().
                findItem(R.id.home_page) : null);
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
    public void SearchSubmitted(String query, boolean searchFragmentShowing) {
        SearchData = query;
        onNavigationItemSelected(navigationView != null ? navigationView.getMenu().findItem(R.id.search) : null);
        navigationView.setCheckedItem(R.id.search);
    }

    @Override
    public void onFragmentInteraction(boolean refreshState) {

    }

    @Override
    public void onItemClicked(int id) {
        switch (id)
        {
            case 1:
                onNavigationItemSelected(navigationView != null ? navigationView.getMenu().findItem(R.id.news_favorites) : null);
                break;
            case 2:
                onNavigationItemSelected(navigationView != null ? navigationView.getMenu().findItem(R.id.all_dict) : null);
                break;
        }
    }

    @Override
    public void onMoreMenuClicked() {
        Intent intent = new Intent(this, DictionaryMenu.class);
        this.startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(SearchFrag searchFrag) {
        /*if (!SearchData.isEmpty())
            searchFrag.callToFetchData(SearchData);
        SearchData = "";*/
    }


}
