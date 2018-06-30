package com.example.clinton.light.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.clinton.light.dictionary_day.ParseWordDay;
import com.example.clinton.light.dictionary_day.StoreData;
import com.example.clinton.light.dictionary_day.WordDay;
import com.example.clinton.light.receivers.TodayWordReceiver;
import com.example.clinton.light.utilities.MySingleton;

import org.json.JSONObject;

public class FetchTodayWordService extends IntentService {

    public WordDay wordDay;
    final String url =
            "http://api.wordnik.com:80/v4/words.json/wordOfTheDay?api_key=f6b04740a4001e4601904640c0f9342e1dc96693c3f70bfba";
    public FetchTodayWordService () {
        super("FetchTodayWordService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SERVICE", "started");
        MakeRequest(intent);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent (Intent intent) {
        Log.i("OUTPUT", "Service called");
        MakeRequest(intent);
    }
    public void MakeRequest(final Intent intent)
    {
        if (intent == null) return;
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse (JSONObject response) {
                                wordDay = ParseWordDay.ParseJSON(response);
                                if(wordDay != null)
                                    new StoreData().DoWork(FetchTodayWordService.this, intent);
                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse (VolleyError error) {
                                        TodayWordReceiver.completeWakefulIntent(intent);
                                        Log.i("OUTPUT", String.valueOf(error));
                                        stopSelf();
                                    }
                                });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
