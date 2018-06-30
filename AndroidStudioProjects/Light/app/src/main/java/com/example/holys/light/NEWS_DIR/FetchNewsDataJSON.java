package com.example.holys.light.NEWS_DIR;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.holys.light.TabsFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class FetchNewsDataJSON extends AsyncTask<TabsFragment, Void, List<NewsFacade>> {

    TabsFragment _fragment;

    @Override
    protected List<NewsFacade> doInBackground (TabsFragment... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        _fragment = params[0];

        try {

            final String baseUri = "http://content.guardianapis.com/search?";
            Uri uriBuilder = Uri.parse(baseUri)
                    .buildUpon()
                    //.appendQueryParameter("section", "world")
                    .appendQueryParameter("order-by", "newest")
                    .appendQueryParameter("use-date", "published")
                    .appendQueryParameter("show-fields", "trailText,thumbnail")
                    .appendQueryParameter("page", String.valueOf(TabsFragment.pageSize))
                    .appendQueryParameter("page-size", "1")
                    .appendQueryParameter("api-key", "255d0959-a40e-4fb5-98ae-4d3914d5009a")
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
            PassJSONForData passer = new PassJSONForData();
            return passer.PassJSON(forecastJsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        }

        finally {
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
    }

    private void SaveToDataBase()
    {

    }

    @Override
    protected void onPostExecute(List<NewsFacade> data)
    {



        /*_fragment.progressBar.setVisibility(View.GONE);
        _fragment._isLoading = false;
        try
        {
            if (data != null) {
                //Collections.reverse(data);
                int count = _fragment.mAdapter.getItemCount();
                _fragment.mNewsData.addAll(data);
                _fragment.mAdapter.notifyItemRangeInserted(count, data.size() - 1);
                //_fragment.recyclerView.smoothScrollToPosition(count);
                Toast.makeText(_fragment.getContext(), "News updated", Toast.LENGTH_SHORT).show();
                TabsFragment.pageSize++;
            }
            else
            {
                Toast.makeText(_fragment.getContext(), "Come check later", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
            Log.i("UPDATE", "Update Error");
        }
        */
    }

}
