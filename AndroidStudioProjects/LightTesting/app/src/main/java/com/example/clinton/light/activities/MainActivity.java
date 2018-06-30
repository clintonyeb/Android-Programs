package com.example.clinton.light.activities;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clinton.light.R;
import com.example.clinton.light.menuFragments.AskMeFragment;
import com.example.clinton.light.menuFragments.DictionaryFrag;
import com.example.clinton.light.menuFragments.TodayNewsFragment;
import com.example.clinton.light.receivers.TodayWordReceiver;
import com.example.clinton.light.utilities.DialogFacade;
import com.example.clinton.light.utilities.DialogFrag;
import com.example.clinton.light.utilities.DictDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DialogFrag.NoticeDialogListener,
        DictDialog.DictDialogListener,
        AskMeFragment.AskMeReadyToLoadData,
        DictionaryFrag.DictionaryReadyToLoadData{

    public ContentResolver mResolver;
    Menu menu;
    NavigationView navigationView;
    FloatingActionsMenu floatMenu;
    private long mBackPressed;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    FloatingActionButton news_fab;
    FloatingActionButton dict_fab;
    Fragment CurrentFragment = null;

    public static final boolean DEVELOPER_MODE = true;
    private static final int TIME_INTERVAL = 2000;
    public static boolean CULTURE_STATE = false;
    public static boolean LIFESTYLE_STATE = false;
    public static boolean SCIENCE_STATE = false;
    public static boolean SPORT_STATE = false;
    public static boolean WORLD_STATE = false;
    public static boolean SEARCH_STATE = false;
    public static boolean DICTIONARY_STATE = false;
    public  String SEARCH_WORD = "";
    DialogFacade dialogFacade = null ;




    public static void SetStrictMode() {
        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()  // or .detectAll() for all detectable problems
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
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetStrictMode();
        SetUpUI();
        SetAlarm();
    }

    private void SetUpUI()
    {
       InitialiseViews();
        InitializeMembers();
        SetEvents();
    }

    private void InitializeMembers() {
        mResolver = getContentResolver();
    }

    private void InitialiseViews()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        floatMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        news_fab = (FloatingActionButton) findViewById(R.id.fab_news);
        dict_fab  = (FloatingActionButton) findViewById(R.id.fab_dict);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void SetEvents()
    {
            news_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    floatMenu.collapse();
                    showNoticeDialog();
                }
            });

            dict_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    floatMenu.collapse();
                    ShowDictDialog();
                }
            });

            navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView != null ? navigationView.getMenu().findItem(R.id.news) : null);
        navigationView.setCheckedItem(R.id.news);
    }
    public void showNoticeDialog() {
        DialogFragment dialog = new DialogFrag();
        dialog.show(getFragmentManager(), "DialogFrag");
    }

    private void ShowDictDialog() {
        DialogFragment dialog = new DictDialog();
        dialog.show(getFragmentManager(), "DictDialog");
    }

    @Override
    public View onCreateView (String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onBackPressed() {
//        Fragment f = getSupportFragmentManager().findFragmentById(R.id.MainFrameLayout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);

            }
        }
        if (CurrentFragment instanceof TodayNewsFragment) {
            ExitActivity();
        } else {
            onNavigationItemSelected(navigationView != null ? navigationView.getMenu().findItem(R.id.news) : null);
            //navigationView.getMenu().getItem(1).setCheckable(true);
            navigationView.setCheckedItem(R.id.news);
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

    private void SetAlarm() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);

        int alarmType = AlarmManager.RTC_WAKEUP;
        Intent intent = new Intent(this, TodayWordReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(getApplicationContext(),
                1, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUp)
        {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(alarmType, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(MainActivity.this, "ALarm already set", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }


        switch (id)
        {
            case R.id.news:
                CurrentFragment = new TodayNewsFragment();
                break;

            case R.id.dict:
                CurrentFragment = new DictionaryFrag();
                break;

            case R.id.askme:
                CurrentFragment = new AskMeFragment();
                break;
            case R.id.notify:
                break;


            case R.id.profile:
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
        InitializeFragment(item);
        return true;
    }

    private void InitializeFragment(MenuItem item)
    {
        if (CurrentFragment != null)
        {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.MainFrameLayout, CurrentFragment).commit();
            this.setTitle(item.getTitle());
        }
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, DialogFacade facade) {
            dialogFacade = facade;
            onNavigationItemSelected(navigationView != null ? navigationView.getMenu().findItem(R.id.askme) : null);
            navigationView.setCheckedItem(R.id.askme);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog, DialogFacade facade) {
    }

    @Override
    public void onDictDialogPositiveClick(DialogFragment dialog) {
        EditText editText = (EditText) dialog.getDialog().findViewById(R.id.search_news);
        String word = editText.getText().toString();
        if (!word.isEmpty()) {
            SEARCH_WORD = MakeWord(word);
            onNavigationItemSelected(navigationView != null ? navigationView.getMenu().findItem(R.id.dict) : null);
            navigationView.setCheckedItem(R.id.dict);
        }
    }

    @Override
    public void onDictDialogNegativeClick(DialogFragment dialog) {

    }

    private static String MakeWord(String word) {
        word = word.trim();
        word = word.toLowerCase();
        word = word.trim();
        word = word.replaceAll("\\s+", " ");
        word = word.replace(" ", "+");
        return word;
    }

    @Override
    public void BeginLoadData() {
        AskMeFragment fragment = (AskMeFragment)getSupportFragmentManager()
                .findFragmentById(R.id.MainFrameLayout);
        if (fragment != null && dialogFacade != null)
        {
           fragment.CallFetchData(dialogFacade);
            dialogFacade = null;
        }
    }


    @Override
    public void BeginDictionaryLoadData() {
        DictionaryFrag fragment = (DictionaryFrag)getSupportFragmentManager()
                .findFragmentById(R.id.MainFrameLayout);
        if (fragment != null && !SEARCH_WORD.isEmpty())
        {
            fragment.CallForResult(SEARCH_WORD);
            SEARCH_WORD = "";
            DICTIONARY_STATE = true;
        }
    }
}
