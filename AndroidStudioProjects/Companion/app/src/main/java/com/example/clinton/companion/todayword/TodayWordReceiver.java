package com.example.clinton.companion.todayword;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.clinton.companion.activities.TodayWordActivity;
import com.example.clinton.companion.home.FetchQuote;
import com.example.clinton.companion.utilities.HelperMethods;

public class TodayWordReceiver extends WakefulBroadcastReceiver {
    public TodayWordReceiver() {}

    @Override
    public void onReceive (Context context, Intent intent) {
        if (!HelperMethods.CompareDate(TodayWordActivity.TODAY_WORD_ID, context) && HelperMethods.timeAfter())
        {
            Intent serIntent1 ;
            serIntent1 = new Intent(context, FetchTodayWordService.class);
            startWakefulService(context, serIntent1);
            serIntent1 = new Intent(context, FetchQuote.class);
            startWakefulService(context, serIntent1) ;
        }
    }
}
