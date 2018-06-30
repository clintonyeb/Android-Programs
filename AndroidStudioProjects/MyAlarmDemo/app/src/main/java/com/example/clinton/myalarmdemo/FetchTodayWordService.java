package com.example.clinton.myalarmdemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class FetchTodayWordService extends IntentService {

    public FetchTodayWordService () {
        super("FetchTodayWordService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SERVICE", "started");
        DoWork(intent);
        return START_STICKY;
    }

    public FetchTodayWordService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent (Intent intent) {
        Log.i("SERVICE", "handleIntent");
      DoWork(intent);
    }

    private void DoWork(Intent intent)
    {
        if (intent == null) return;
        Log.i("SERVICE", "Doing Work");
        WordNotify.notify(getApplicationContext(),"Word Head", "Word Meaning" );
        //WakeLocker.release();
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SERVICE", "destroyed");

    }
}
