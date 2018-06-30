package com.example.clinton.companion.home;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.clinton.companion.utilities.VolleySingleton;

import org.json.JSONObject;

public class FetchQuote extends IntentService {

    String url = "http://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en";
    public FetchQuote() {
        super("FetchQuote");
    }

    public static void startService(Context context) {
        Intent intent = new Intent(context, FetchQuote.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            handleFetchingNews();
        }
    }

    private void handleFetchingNews() {
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        new HandleQuoteAsync().execute(response, getApplicationContext());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("QUOTE", " " + error);
                    }
                }
                );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

}
