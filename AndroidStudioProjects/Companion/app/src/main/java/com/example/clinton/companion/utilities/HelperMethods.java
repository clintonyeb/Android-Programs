package com.example.clinton.companion.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

import com.example.clinton.companion.activities.MainActivity;
import com.example.clinton.companion.news.workers.HandleNewsData;
import com.example.clinton.companion.news.facades.NewsResults;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HelperMethods {
    static SharedPreferences preferences;

    public static boolean CheckConnectionState(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static String MakeWord(String word)
    {
        word = word.trim();
        word = word.toLowerCase();
        word = word.trim();
        word = word.replaceAll("\\s+", " ");
        word = word.replace(" ", "+");
        return word;
    }

    public static boolean CheckAppStart(String prefName, Context context)
    {
        preferences = context.getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);
        return preferences.getBoolean(prefName, true);
    }
    public static void UpdateDate(String prefName, Context context)
    {
        Calendar calendar = Calendar.getInstance();
        preferences =  context.getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);
        preferences.edit().putLong(prefName, calendar.getTimeInMillis()).apply();
    }
    public static boolean CompareDate(String prefName, Context context)
    {
        Calendar calendar = Calendar.getInstance();
        preferences = context.getSharedPreferences(MainActivity.PREF_FILE, Context.MODE_PRIVATE);
        long previousTime = preferences.getLong(prefName, 0);
        calendar.setTimeInMillis(previousTime);
       return MyDateUtils.isToday(calendar);
    }

    public static boolean timeAfter()
    {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
            Date nine = parser.parse("08:59");
            Date now = new Date();

            int hr = now.getHours();
            int min = now.getMinutes();
            String val =  String.valueOf(hr) + ":" + String.valueOf(min);

            Date userDate = parser.parse(val);
            if (userDate.after(nine)) return true;
        } catch (ParseException e) {
            System.err.println("error "+ e);
        }
        return false;
    }

    public static void sendBroadCast(NewsResults newsResults, Context context)
    {
        Intent registrationComplete = new Intent(QuickstartPreferences.SERVICE_COMPLETE);
        registrationComplete.putExtra(HandleNewsData.SERVICE_RESULT, newsResults);
        LocalBroadcastManager.getInstance(context).sendBroadcast(registrationComplete);
    }
}
