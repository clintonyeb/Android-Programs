package com.example.clinton.light.dict_other;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.clinton.light.activities.DictOther;
import com.example.clinton.light.utilities.MySingleton;

import org.json.JSONArray;

import java.util.List;

public class FetchDataDict {
    Context context;
    public void MakeRequest(final Context context, final DictOther view, String url)
    {
        this.context = context;

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<DictOtherFacade>  facadeList = new ParseDataDict().ParseJSON(response);
                        new StoreDictOther().execute(context, facadeList);

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
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

}
