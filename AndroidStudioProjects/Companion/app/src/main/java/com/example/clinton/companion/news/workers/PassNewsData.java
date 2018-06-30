package com.example.clinton.companion.news.workers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.clinton.companion.news.facades.NewsFacade;
import com.example.clinton.companion.news.facades.NewsFeatures;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.HelperMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;


public class PassNewsData {

    Context context;

    public void PassJSON(JSONObject JSON, Context c, NewsFeatures newsFeatures) {
        String title;
        String date;
        String text;
        String webAddress;
        String sectionName;
        context = c;

        NewsFacade facade;
        try {
            JSONObject response = JSON.getJSONObject("response");
            String status = response.getString("status");
            if (status.equalsIgnoreCase("ok")) {
                JSONArray results = response.getJSONArray("results");
                if (results.length() < 1)
                    return;
                int i = 0;
                while (i < results.length()) {
                    try {
                        JSONObject data = results.getJSONObject(i);
                        try {
                            title = data.getString("webTitle");
                        }catch (JSONException e)
                        {
                            title = "";
                        }

                        try {
                            date = data.getString("webPublicationDate");
                        }
                        catch (JSONException e)
                        {
                            date = "";
                        }

                        Bitmap bitmap;
                        String imageUrl;
                        JSONObject fields;
                        fields = data.getJSONObject("fields");
                        try {
                            text = fields.getString("trailText");

                        }catch (JSONException e)
                        {
                            Log.e("JSON_ERROR ", " " + e);
                            text = "";
                        }

                        try {
                            imageUrl = fields.getString("thumbnail");
                            bitmap = getImage(imageUrl);
                        }
                        catch (JSONException e)
                        {
                            bitmap = null;
                        }
                        try {
                            webAddress = data.getString("webUrl");
                        }catch (JSONException e)
                        {
                            webAddress = "";
                        }
                        try {
                            sectionName = "#" + data.getString("sectionName");
                        }catch (JSONException e)
                        {
                            sectionName = "";
                        }

                        facade = new NewsFacade(title, date, text, bitmap, webAddress, sectionName);

                        StoreData store =  new StoreData(facade, context, newsFeatures.getFragmentID());
                        if (newsFeatures.isCanClear())
                        {
                            newsFeatures.setCanClear(false);
                            store.DeleteData();
                        }
                        store.StoringData();
                        if (i == results.length() - 1)
                        {
                           HelperMethods.sendBroadCast(NewsResults.Success, context);
                        }
                    } catch (JSONException e) {
                        Log.e("PassResults", e.toString());
                    }

                    i++;
                }
            }
        } catch (JSONException e) {
            Log.e("PassResults", e.toString());
        }
    }

    private Bitmap getImage(String uri) {
        Bitmap bmp;
        try {
            URL url = new URL(uri);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e)
        {
            return null;
        }
        return bmp;
    }


}
