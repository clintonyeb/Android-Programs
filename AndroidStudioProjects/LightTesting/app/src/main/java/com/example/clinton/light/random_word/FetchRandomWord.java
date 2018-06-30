package com.example.clinton.light.random_word;

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
import com.example.clinton.light.activities.RandomWord;
import com.example.clinton.light.utilities.MySingleton;

import org.json.JSONObject;

import java.util.List;

public class FetchRandomWord {
    Context context;

    public void MakeRequest(final Context context, final RandomWord view, String url)
    {
        this.context = context;

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<RandomFacade> facades = new ParseRandomWord().ParseJSON(response);
                        if(facades != null)
                            new StoreRandomWord().execute(context, facades);

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
	                    view.resetUpdating();
                    }

                }

                );
        Log.i("FETCH RANDOM", "called");
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
