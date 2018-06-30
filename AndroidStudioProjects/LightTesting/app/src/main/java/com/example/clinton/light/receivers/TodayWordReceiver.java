package com.example.clinton.light.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.clinton.light.services.FetchTodayWordService;

public class TodayWordReceiver extends WakefulBroadcastReceiver {
    public TodayWordReceiver() {}

    @Override
    public void onReceive (Context context, Intent intent) {
        Log.i("Receiver", "Called here");
        Intent serIntent1 = new Intent(context, FetchTodayWordService.class);
        startWakefulService(context, serIntent1);
    }
}
