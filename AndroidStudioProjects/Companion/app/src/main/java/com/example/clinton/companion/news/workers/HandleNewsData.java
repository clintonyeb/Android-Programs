package com.example.clinton.companion.news.workers;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.clinton.companion.news.facades.NewsFeatures;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.HelperMethods;
import com.example.clinton.companion.utilities.VolleySingleton;

import org.json.JSONObject;

public class HandleNewsData extends AsyncTask<Object, Void, Void> {
    org.json.JSONObject JSONObject;
    Context context;
    NewsFeatures newsFeatures;
    public static final String SERVICE_RESULT = "service_result";

    @Override
    protected Void doInBackground(Object... params) {
        JSONObject = (org.json.JSONObject)params[0];
        context = (Context)params[1];
        newsFeatures = (NewsFeatures)params[2];
        PassNewsData passer = new PassNewsData();
        passer.PassJSON(JSONObject, context, newsFeatures);
        return null;
    }

    public void handleFetchingNews(final Context cont, final NewsFeatures newsFeatures) {
        String url = String.valueOf(GetWhichOne.GetURL(newsFeatures.getFragmentID(), newsFeatures.getPageNumber()));
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<org.json.JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        new HandleNewsData().execute(response, cont, newsFeatures);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NewsResults newsResults;
                        if (error instanceof TimeoutError) {
                            newsResults = NewsResults.Timeout;
                        } else if (error instanceof NoConnectionError) {
                            newsResults = NewsResults.NoConnection;
                        } else if (error instanceof NetworkError) {
                            newsResults = NewsResults.Network;
                        } else {
                            newsResults = NewsResults.Unknown;
                        }
                        HelperMethods.sendBroadCast(newsResults, context);
                    }
                }
                );
        VolleySingleton.getInstance(cont).addToRequestQueue(jsonObjectRequest);
    }
}
