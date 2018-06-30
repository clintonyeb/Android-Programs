package com.example.clinton.myalarmdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("RECEIVER", "called");
        //WakeLocker.acquire(context);
        Intent serIntent1 = new Intent(context, FetchTodayWordService.class);
        context.startService(serIntent1);
    }

}
