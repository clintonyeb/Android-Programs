package com.example.clinton.light.askme;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.clinton.light.newsdir.NewsFacade;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ParseData {
    List<NewsFacade> newsData;

    public List<NewsFacade> PassJSON(JSONObject JSON)
    {
        String title;
        String date;
        String text;
        String webAddress;
        String sectionName;

        newsData = new ArrayList<>();
        try
        {
            JSONArray results = JSON.getJSONArray("results");
                int i = 0;
                while (i < results.length())
                {
                    try
                    {
                        JSONObject data = results.getJSONObject(i);
                        title = data.getString("title");
                        date = data.getString("date");
                        text = data.getString("kwic");
                        String imageUrl = data.getString("iurl");
                        webAddress = data.getString("url");
                        sectionName = data.getString("domain");
                        newsData.add(new NewsFacade(title, date, text, getImage(imageUrl), webAddress, sectionName));
                    }
                    catch (JSONException e)
                    {
                        Log.e("PassResults", e.toString());
                    }

                    i++;
                }
        }
        catch (JSONException e)
        {
            Log.e("PassResults", e.toString());
        }

        return newsData;
    }

    private Bitmap getImage(String uri)
    {
        Log.e("Image URL", uri);
        Bitmap bmp = null;
        try
        {
            URL url = new URL(uri);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        }
        catch (MalformedURLException e)
        {
            Log.e("Image", e.toString());
        }
        catch (IOException e)
        {
            Log.e("Image", e.toString());
        }
        return bmp;
    }
}
