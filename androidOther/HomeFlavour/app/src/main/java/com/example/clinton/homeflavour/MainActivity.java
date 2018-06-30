package com.example.clinton.homeflavour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            ActivityInterface, HomeFragment.OnFragmentInteractionListener,
        AllTabs.OnFragmentInteractionListener{

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    Fragment currentFragment;
    Fragment previousFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setEvents();
        initializeMembers();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        previousFragment = currentFragment;
        switch (id)
        {
            case R.id.homeItem:
                currentFragment = new AllTabs();
                this.setTitle(this.getString(R.string.home));
                break;
        }

        setMenuItemChecked(item);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        /*navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_drawer);*/
        navigationView.getMenu().findItem(item.getItemId()).setChecked(true);
    }

    @Override
    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);

    }

    @Override
    public void setEvents() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void initializeMembers() {
        onNavigationItemSelected(navigationView != null ? navigationView.getMenu().
                findItem(R.id.homeItem) : null);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
