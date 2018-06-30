package com.example.clinton.light.fetchData;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.clinton.light.dictionary_main.StoreRecentWord;
import com.example.clinton.light.menuFragments.DictionaryFrag;
import com.example.clinton.light.random_word.ParseRandomWord;
import com.example.clinton.light.random_word.RandomFacade;
import com.example.clinton.light.utilities.MySingleton;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by clinton on 8/4/16.
 */
public class FetchNews {

    Context context;
    public static final String SERVICE_DATA = "serviceData";
    public static final String SERVICE_STATE = "state";
    public static final String SEARCH_DATA  ="search";
    public static String TO_SEARCH ;
    public static int POSITION = 2;
    public static String POSITION_DATA;

    public void MakeRequest(final Context context, final DictionaryFrag view, String url)
    {
        this.context = context;

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("fetchword", "CALLED");

                        List<RandomFacade> facades = new ParseRandomWord().ParseJSON(response);
                        if(facades != null)
                            new StoreRecentWord().execute(context, facades);
                        else view.onLoadFinished(null, null);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error instanceof TimeoutError)
                        {
                            Toast.makeText(context, "Network too slow!", Toast.LENGTH_LONG).show();
                        }
                        else if(error instanceof NoConnectionError)
                        {
                            Toast.makeText(context, "Not connected to internet", Toast.LENGTH_LONG).show();
                        }
                        else if(error instanceof NetworkError)
                        {
                            Toast.makeText(context, "Check your network!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                        view.onLoadFinished(null, null);

                    }


                }


                );
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}