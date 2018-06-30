package com.example.holys.allin1;

import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button browseButton;
    Button pictureButton;
    Button callButton;
    Button mapsButton;
    Button contactsButton;
    Button smsButton;
    String longitude;
    String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        browseButton = (Button) findViewById(R.id.browseButton);
        pictureButton = (Button) findViewById(R.id.pictureButton);
        callButton = (Button) findViewById(R.id.callButton);
        mapsButton = (Button) findViewById(R.id.mapsButton);
    }

    public void browse_ButtonClick(View view)
    {
        browseButton = (Button) findViewById(R.id.browseButton);
        CallIntent("browseButton");
    }

    public void picture_ButtonClick(View view)
    {
        pictureButton = (Button)findViewById(R.id.pictureButton);
        CallIntent("pictureButton");
    }
    public void call_ButtonClick(View view)
    {
        callButton = (Button) findViewById(R.id.callButton);
        CallIntent("callButton");
    }
    public void mail_ButtonClick(View view)
    {
        mapsButton = (Button)findViewById(R.id.mapsButton);
        CallIntent("mailButton");
    }

    public void sms_ButtonClick(View view)
    {
        smsButton = (Button)findViewById(R.id.smsButton);
        CallIntent("smsButton");
    }

    public  void facebook_ButtonClick(View view)
    {
        Button facebookButton = (Button)findViewById(R.id.facebookButton);
        CallIntent("facebookButton");
    }

    private void twitter_ButtonClick(View view)
    {
        Button twitterButton = (Button)findViewById(R.id.twitterButton);
        CallIntent("twitterButton");
    }

    private void CallIntent(String name)
    {
        Intent intent = null;
        switch (name)
        {
            case "browseButton":
                intent = new Intent(Intent.ACTION_VIEW,  Uri.parse("http://www.google.com"));
                break;
            case "pictureButton":
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                break;
            case "callButton":
                intent = new Intent(Intent.ACTION_DIAL);
                break;
            case "mailButton":
                //GPSEnable();
                //intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse("geo:"+latitude+","+longitude));
                //Toast.makeText(MainActivity.this, latitude + "  " + longitude, Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Email.class);
                break;
            case "smsButton":
                intent = new Intent(this, SMS.class);
                break;
            case "facebookButton":
                try
                {
                    intent = new Intent("android.intent.category.LAUNCHER");
                    intent.setClassName("com.facebook.katana", "com.facebook.katana.LoginActivity");
                }catch (Exception e)
                {
                 intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
                }
                break;
            case "twitterButton":
                try {
                    // get the Twitter app if possible
                    this.getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=<place_user_name_here>"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/<place_user_name_here>"));
                }
                break;
            }


        if (intent != null)
        {
            startActivity(intent);
        }
    }

    private  void GPSEnable()
    {
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            latitude = String.valueOf(gpsTracker.latitude);

            longitude = String.valueOf(gpsTracker.longitude);
            /*

            String country = gpsTracker.getCountryName(this);


            String city = gpsTracker.getLocality(this);

            String postalCode = gpsTracker.getPostalCode(this);


            String addressLine = gpsTracker.getAddressLine(this);
            */

        }
        else
        {
            gpsTracker.showSettingsAlert();
        }

    }

    public void actionButton_onClick(View view)
    {
        //When you click a button, it will change color and effects
        //When another button is clicked, it will reset previous button
        //and the new one will receive the effects
    }
}
