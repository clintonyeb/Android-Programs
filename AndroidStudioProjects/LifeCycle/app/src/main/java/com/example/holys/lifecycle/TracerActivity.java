package com.example.holys.lifecycle;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TracerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracer);
        notify("onCreate");
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        notify("onPause");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        notify("onResume");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        notify("onStop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        notify("onDestroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        notify("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        notify("onSaveInstanceState");
    }

    private  void notify(String methodName)
    {
        String name = this.getClass().getName();
        String[] strings = name.split("\\.");
        Notification notification = new Notification.Builder(this)
                .setContentTitle(methodName + " " + strings[strings.length-1] ).setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(name).build();
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify((int)System.currentTimeMillis(), notification);
    }
}
