package com.example.clinton.companion.todayword;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.clinton.companion.utilities.API_KEYS;
import com.example.clinton.companion.utilities.VolleySingleton;

import org.json.JSONObject;

public class FetchTodayWordService extends IntentService {

    final String url =
            "http://api.wordnik.com:80/v4/words.json/wordOfTheDay?api_key=" + API_KEYS.WORD_NIK_API;
    public WordDay wordDay;
    public FetchTodayWordService() {
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
                                        //TodayWordReceiver.completeWakefulIntent(intent);
                                        Log.i("OUTPUT", String.valueOf(error));
                                    }
                                });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
