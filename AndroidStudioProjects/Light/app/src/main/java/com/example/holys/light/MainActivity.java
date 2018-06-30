package com.example.holys.light;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.news));
    }



    @Override
    public View onCreateView (String name, Context context, AttributeSet attrs) {
        View view =  super.onCreateView(name, context, attrs);
        return view;
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout)
        {
            SharedPreferences pref = this.getSharedPreferences(getResources().getString(R.string.LOGGEDIN),MODE_PRIVATE );
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(getResources().getString(R.string.LOGGEDIN), false);
            editor.apply();
            Navigate();
        }

        return super.onOptionsItemSelected(item);
    }

    private void Navigate()
    {
        Intent intent = new Intent(this, LogInActivty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        Fragment fragment = null;
        switch (id)
        {
            case R.id.announce:

                break;
            case R.id.profile:
            break;
            case R.id.article:
                break;
            case R.id.news:
                fragment = new TodayNewsFragment();
                break;
            case R.id.study:
                fragment = new StudyFragment();
                break;
            case R.id.grievances:
                break;
            case R.id.events:
                break;
            case R.id.help:
                break;
            case R.id.settings:
                break;
            case R.id.feedback:
                break;
            case R.id.logout:
                SharedPreferences pref = this.getSharedPreferences(getResources().getString(R.string.LOGGEDIN),MODE_PRIVATE );
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean(getResources().getString(R.string.LOGGEDIN), false);
                editor.apply();
                Navigate();
                break;
        }

        if (fragment != null)
        {
            FragmentManager manager  = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.MainFrameLayout, fragment).commit();
            this.setTitle(item.getTitle());
        }


        return true;
    }
}
