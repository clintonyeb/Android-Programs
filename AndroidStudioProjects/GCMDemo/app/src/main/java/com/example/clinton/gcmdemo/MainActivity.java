package com.example.clinton.gcmdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                TextView text = (TextView)findViewById(R.id.text);
                text.setText("Success");
            }
        };
        setContentView(R.layout.activity_main);
        if (isGooglePlayServicesAvailable(this))
        {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        else
        {

        }
    }


    String SENDER_ID;
    TextView text;
    GoogleCloudMessaging gcm;
    public void onClick(final View view) {
        SENDER_ID = this.getResources().getString(R.string.sender_id);
        text = (TextView)findViewById(R.id.text);
         gcm = GoogleCloudMessaging.getInstance(this);
        if (view == findViewById(R.id.send)) {
            new AsyncTask() {

                @Override
                protected Object doInBackground(Object[] params) {
                    String msg = "";
                    try {
                        Bundle data = new Bundle();
                        data.putString("my_message", "Hello World");
                        data.putString("my_action","SAY_HELLO");
                        String id = Integer.toString(1);
                        if (SENDER_ID != null)
                        gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
                        msg = "Sent message";
                    } catch (IOException ex) {
                        msg = "Error :" + ex.getMessage();
                    }
                    return msg;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    String msg = (String)o;
                    text.setText(msg + "\n");
                }

            }.execute(null, null, null);
        } /*else if (view == findViewById(R.id.clear)) {
            text.setText("");
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public boolean isGooglePlayServicesAvailable(Context context){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        switch (resultCode)
        {
            case ConnectionResult.SUCCESS:
                return true;
            default:
                googleApiAvailability.getErrorDialog(this, resultCode, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
                return false;
        }
    }
    /*private void registerDevcie()
    {
        String projectId = getResources().getString(R.string.gcm_project_id);
        GoogleCloudMessaging messaging = GoogleCloudMessaging.getInstance(this);
        try {
            String regid =messaging.register(projectId);
            Log.i("REGISTER ID: ", regid);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_RECOVER_PLAY_SERVICES:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Google Play Services must be installed.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
