package com.example.holys.bgcclms;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Created by holys on 2/18/2016.
 */
class GetJSONData extends AsyncTask<NewsDataDisplay, Void, List<NewsDataFacade>> {

    NewsDataDisplay activityDisplay;

    @Override
    protected List<NewsDataFacade> doInBackground (NewsDataDisplay... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        activityDisplay = params[0];

        try {

            final String baseUri = "http://access.alchemyapi.com/calls/data/GetNews?";
            Uri uriBuilder = Uri.parse(baseUri)
                    .buildUpon()
                    .appendQueryParameter("outputMode", "json")
                    .appendQueryParameter("start", "now-1d")
                    .appendQueryParameter("end", "now")
                    .appendQueryParameter("count", "5")
                    .appendQueryParameter("return", "enriched.url.url,enriched.url.title,enriched.url.text,enriched.url.publicationDate")
                    .appendQueryParameter("apikey", "5f302f75e085278aa1ccb66326429de28ac526c1")
                    .build();
            URL url = new URL(uriBuilder.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            //Log.v("Results", forecastJsonStr);
            PassJSONForData passer = new PassJSONForData();
            return passer.PassJSON(forecastJsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        }catch (JSONException e)
        {
            Log.e("PassResults", e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<NewsDataFacade> data)
    {
        if (data != null) {
            Collections.reverse(data);
            activityDisplay.mNewsData.addAll(0, data);
            DisplayCustomAdapter adapter =  new DisplayCustomAdapter(activityDisplay.mNewsData, activityDisplay);
            activityDisplay.recyclerView.setAdapter(adapter);
            activityDisplay.swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(activityDisplay, "Page Refreshed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            activityDisplay.swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(activityDisplay, "Come check later", Toast.LENGTH_SHORT).show();
        }
    }
}
