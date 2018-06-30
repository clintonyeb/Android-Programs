/*
package com.example.clinton.light.askme;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.clinton.light.menuFragments.AskMeFragment;
import com.example.clinton.light.newsdir.NewsFacade;
import com.example.clinton.light.utilities.MySingleton;

import org.json.JSONObject;

import java.util.List;


public class FetchData {
    Context context;
    AskMeFragment fragment;

    public void MakeRequest(final Context context,AskMeFragment view, String url)
    {
        fragment = view;
        this.context = context;

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<NewsFacade> facades = new ParseData().PassJSON(response);
                        if(facades != null)
                            new StoreData().execute(context, facades);
                        else Log.i("PARSING", "error");

                        MakeVisible();
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
                        MakeVisible();
                    }

                }

                );
        Log.i("FETCH RANDOM", "called");
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    private void MakeVisible()
    {
        fragment.editText.setEnabled(true);
        fragment.mRecyclerView.setVisibility(View.VISIBLE);

    }
}
*/
