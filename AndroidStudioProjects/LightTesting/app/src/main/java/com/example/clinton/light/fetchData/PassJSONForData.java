package com.example.clinton.light.fetchData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.clinton.light.R;
import com.example.clinton.light.newsdir.NewsFacade;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clinton on 2/25/2016.
 */
public class PassJSONForData {

    List<NewsFacade> newsData;
    Context context;

    public List<NewsFacade> PassJSON(String JSON, Context c) {
        String title;
        String date;
        String text;
        String webAddress;
        String sectionName;
        context = c;

        newsData = new ArrayList<>();
        try {
            JSONObject JSOnReceived = new JSONObject(JSON);
            JSONObject response = JSOnReceived.getJSONObject("response");
            String status = response.getString("status");
            if (status.equalsIgnoreCase("ok")) {
                JSONArray results = response.getJSONArray("results");
                if (results.length() < 1)
                    return null;
                int i = 0;
                while (i < results.length()) {
                    try {
                        JSONObject data = results.getJSONObject(i);
                        title = data.getString("webTitle");
                        date = data.getString("webPublicationDate");
                        JSONObject fields = data.getJSONObject("fields");
                        text = fields.getString("trailText");
                        Bitmap bitmap = null;
                        String imageUrl = "";
                        try {
                            imageUrl = fields.getString("thumbnail");
                            bitmap = getImage(imageUrl);
                        }
                        catch (JSONException e)
                        {
                            bitmap = BitmapFactory.decodeResource(context.getResources(),
                                    R.drawable.tool);
                        }
                        webAddress = data.getString("webUrl");
                        sectionName = data.getString("sectionName");

                        newsData.add(new NewsFacade(title, date, text, bitmap, webAddress, sectionName));
                    } catch (JSONException e) {
                        Log.e("PassResults", e.toString());
                    }

                    i++;
                }
            }
        } catch (JSONException e) {
            Log.e("PassResults", e.toString());
        }

        return newsData;
    }

    private Bitmap getImage(String uri) {
        Bitmap bmp = null;
        try {
            URL url = new URL(uri);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }
        return bmp;
    }

}
