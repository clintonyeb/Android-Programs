package com.example.clinton.homeflavour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InitializeItems,
        TabsFragment.OnFragmentInteractionListener, MenuFragment.OnFragmentInteractionListener,
OfferFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActivity";
    public static final String LOGIN_VALID = "LoginValid";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    Fragment currentFragment;
    Fragment previousFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkValidLogin())
        {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            initializeViews();
            setEvents();
            initializeMembers();
        }

    }

    private boolean checkValidLogin()
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(LOGIN_VALID, MODE_PRIVATE);
        boolean valid = preferences.getBoolean("login", false);
        if (!valid)
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        previousFragment = currentFragment;

        switch (id)
        {
            case R.id.log_out:
                logOut();
                break;
            case R.id.main:
                currentFragment = TabsFragment.newInstance(null, null);
                this.setTitle(getString(R.string.app_name));
                break;
        }

        setMenuItemChecked(item);

        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        initializeFragment();
        return true;
    }

    private void initializeFragment()
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


    private void logOut()
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(MainActivity.LOGIN_VALID, MODE_PRIVATE);
        preferences.edit().putBoolean("login", false).apply();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void initializeViews() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void setEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Place Order Here", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void initializeMembers() {
        onNavigationItemSelected(navigationView != null ? navigationView.getMenu().
                findItem(R.id.main) : null);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
