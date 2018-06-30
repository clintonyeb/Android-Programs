package com.example.holys.light.NEWS_DIR;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by holys on 2/25/2016.
 */
public class PassJSONForData {

    List<NewsFacade> newsData;
    public List<NewsFacade> PassJSON(String JSON)
    {
        String title;
        String date;
        String text;
        Bitmap thumbnail;
        String webAddress;
        String sectionName;

        newsData = new ArrayList<>();
        try
        {
            JSONObject JSOnReceived = new JSONObject(JSON);
            JSONObject response = JSOnReceived.getJSONObject("response");
            String status = response.getString("status");
            if(status.equalsIgnoreCase("ok"))
            {
                JSONArray results = response.getJSONArray("results");
                int i = 0;
                while (i < results.length())
                {
                    try
                    {
                        JSONObject data = results.getJSONObject(i);
                        title = data.getString("webTitle");
                        date = data.getString("webPublicationDate");
                        //date = ParseDate(date);
                        JSONObject fields = data.getJSONObject("fields");
                        text = fields.getString("trailText");
                        String imageUrl = fields.getString("thumbnail");
                        thumbnail = getImage(imageUrl);
                        webAddress = data.getString("webUrl");
                        sectionName = data.getString("sectionName");
                        newsData.add(new NewsFacade(title, date, text, thumbnail, webAddress, sectionName));
                    }
                    catch (JSONException e)
                    {
                        Log.e("PassResults", e.toString());
                    }

                    i++;
                }
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
        Bitmap bmp = null;
        try
        {
            URL url = new URL(uri);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        catch (MalformedURLException e)
        {

        }
        catch (IOException e)
        {

        }
        return bmp;
    }


}
