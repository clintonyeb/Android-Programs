package com.demo.clinton.drsnappy;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.demo.clinton.drsnappy.database.AppContentProvider;
import com.demo.clinton.drsnappy.database.SnapState;
import com.demo.clinton.drsnappy.database.Snapshot;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getLoaderManager().initLoader(0, null, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final FlowContentObserver observer = new FlowContentObserver();

        observer.registerForContentChanges(this, Snapshot.class);

        observer.addModelChangeListener(new FlowContentObserver.OnModelStateChangedListener() {

            @Override
            public void onModelStateChanged(@Nullable Class<?> table, BaseModel.Action action, @NonNull SQLCondition[] primaryKeyValues) {
                System.out.println("data chnaged");
                observer.unregisterForContentChanges(MainActivity.this);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        //testDB();
    }

    private void testDB() {
        Snapshot snapshot = new Snapshot();
        snapshot.setState(SnapState.COMPLETED);
        snapshot.setTimeStamp(new Date());
        snapshot.setUserId(1);
        snapshot.save();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = new CursorLoader(MainActivity.this,
                AppContentProvider.Snapshot.SNAP_SHOT_URI, null, null,
                null, null);
        if (loader != null){
            System.out.println("Cursor is good");
        }
        else System.out.println("Cursor null");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToNext())
            System.out.println("Cursor data: " + data.getString(2));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        System.out.println(loader);
    }

    public void buttonClicked(View view) {
        testDB();
    }
}
