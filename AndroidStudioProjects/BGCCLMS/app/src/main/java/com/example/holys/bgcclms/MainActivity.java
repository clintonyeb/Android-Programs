package com.example.holys.bgcclms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;

//import android.app.FragmentTransaction;
//import android.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.onItemSelectedInterface {

    String[] mItems;
    ArrayAdapter mAdapter;
    ListView mListView;

    final String APP_ID = "8235EF19-054A-877D-FF59-58E7F86AA200";
    final String SECRET_ID = "54074F35-D5E3-77BF-FF6B-6029828D1A00";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar1 = (Toolbar)findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle(R.string.second_titleBar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String apVersion = "v1";
        Backendless.initApp(this, APP_ID, SECRET_ID, apVersion);
        BackendlessUser user = new BackendlessUser();
        user.setEmail("holyspirit19941029@hotmail.com");
        user.setPassword("1234567890");

        Backendless.UserService.register(user, new BackendlessCallback<BackendlessUser>() {
            @Override
            public void handleResponse (BackendlessUser backendlessUser) {
                Log.v("REGISTRATION", backendlessUser.getEmail() + "was successful");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public View onCreateView (String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemSelected (String message) {
        //TextView textView = (TextView)findViewById(R.id.detail_text_view);
        //textView.setText(message);
        //InnerListItemsFragment fragment = new InnerListItemsFragment();
        mItems = new String[]{};

        switch (message)
        {
            case "Announcement":
                mItems = new String[]
                        {

                        };
                break;
            case "Articles":
                mItems = new String[]
                        {

                        };
                break;

            case "Profile":
                mItems = new String[]
                        {
                                "Attendance",
                                "MST Scores",
                                "Assessment",
                                "Change Password"

                        };
                break;

            case "School Events":
                mItems = new String[]
                        {

                        };
                break;

            case "World News":
                mItems = new String[]
                        {
                                "Science & Technology",
                                "Health",
                                "Business",
                                "Sports",
                                "Arts & Entertainment",
                                "Culture & Politics",
                                "Gaming",
                                "Law and Crime",
                                "Computers and Internet",
                                "Weather",
                                "Recreation",
                                "Religion"

                        };
                break;
            case "Study Online":
                mItems = new String[]
                        {

                        };
                break;

            case "Grievances":
                mItems = new String[]
                        {

                        };
                break;

        }

        mAdapter = new ArrayAdapter(this, R.layout.detail_list_item, R.id.detail_list_item_textView,  mItems);
        mListView = (ListView)findViewById(R.id.detailListView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                mListView.setItemChecked(position, true);
                Intent intent = new Intent(MainActivity.this, NewsDataDisplay.class);
                intent.putExtra(Intent.EXTRA_TEXT, position);
                startActivity(intent);
            }
        });
        setTitle(message);
    }
}
