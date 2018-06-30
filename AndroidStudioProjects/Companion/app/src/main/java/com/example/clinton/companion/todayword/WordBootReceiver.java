package com.example.clinton.companion.todayword;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class WordBootReceiver extends BroadcastReceiver {
    public WordBootReceiver() {
    }

    public static void SetAlarm(Context context) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);

        int alarmType = AlarmManager.RTC_WAKEUP;
        Intent intent = new Intent(context, TodayWordReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(context,
                1, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUp)
        {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(alarmType, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SetAlarm(context);
        }

    }
}
