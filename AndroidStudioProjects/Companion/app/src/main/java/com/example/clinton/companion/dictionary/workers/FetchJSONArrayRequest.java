package com.example.clinton.companion.dictionary.workers;

import android.content.Context;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.FRAGMENT_ID;
import com.example.clinton.companion.utilities.HelperMethods;
import com.example.clinton.companion.utilities.VolleySingleton;

import org.json.JSONArray;

public class FetchJSONArrayRequest  {
    Context context;

    public void MakeRequest(final Context context, String url, final FRAGMENT_ID fragmentId)
    {
        this.context = context;
        JsonArrayRequest jsonArrayRequest =
            new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    new HandleArrayAsync().execute(response, context, fragmentId);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error instanceof TimeoutError)
                    {
                        HelperMethods.sendBroadCast(NewsResults.Timeout, context);
                    }
                    else if(error instanceof NoConnectionError)
                    {
                        HelperMethods.sendBroadCast(NewsResults.NoConnection, context);
                    }
                    else if(error instanceof NetworkError)
                    {
                        HelperMethods.sendBroadCast(NewsResults.Network, context);
                    }
                    else
                    {
                        HelperMethods.sendBroadCast(NewsResults.Unknown, context);
                    }
                }
            }

            );
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

}