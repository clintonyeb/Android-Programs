package com.example.clinton.light.fetchData;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.clinton.light.database.NewsContract;
import com.example.clinton.light.newsdir.NewsFacade;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class FetchNewsIntentService extends IntentService
{

    ContentResolver resolver;
    private boolean SUCCESS = false;

    public static final String SERVICE_DATA = "serviceData";
    public static final String SERVICE_STATE = "state";
    public static final String SEARCH_DATA  ="search";
    public static String TO_SEARCH ;
    public static int POSITION = 2;
    public static String POSITION_DATA;

    public FetchNewsIntentService() {
        super("FetchNewsIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Fetching News", Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        if (!SUCCESS) Toast.makeText(this, "Problem Fetching Data", Toast.LENGTH_SHORT).show();
        stopSelf();
        super.onDestroy();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Context context =  getApplicationContext();
            resolver = context.getContentResolver();
           int extra = intent.getIntExtra(SERVICE_DATA, -1);
            boolean bool = intent.getBooleanExtra(SERVICE_STATE, false);

            try {
                TO_SEARCH = intent.getStringExtra(SEARCH_DATA);
                POSITION = intent.getIntExtra(POSITION_DATA, 2);
            }
            catch (Exception e)
            {

            }
            URL url = GetWhichOne.GetURL(extra);
            Uri uri = GetWhichOne.GetContentURI(extra);
            PerformAction(uri, bool, url);
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void PerformAction(Uri uri, boolean state, URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
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
                return;
            }
            forecastJsonStr = buffer.toString();
            PassJSONForData passer = new PassJSONForData();
            List<NewsFacade> newsFacades = passer.PassJSON(forecastJsonStr, getApplicationContext());
            if (newsFacades != null)
            {
                if(state){DeleteRows(uri);}
                InsertIntoTable(newsFacades, uri);
                SUCCESS = true;
            }

        } catch (IOException e) {
            Log.e("Fetching News", "Error ", e);
            SUCCESS = false;

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
            //stopSelf();

        }
    }

    private byte[] EncodeImage(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        return stream.toByteArray();
    }

    private void DeleteRows(Uri uri)
    {
        resolver.delete(uri, null, null);
    }

    private void InsertIntoTable(List<NewsFacade> data,Uri uri) {
        for (NewsFacade facade :
                data) {
            ContentValues values = new ContentValues();
            values.put(NewsContract.DataContract.COLUMN_NAME_DATE, facade.getDate());
            values.put(NewsContract.DataContract.COLUMN_NAME_CONTENT, facade.getText());
            values.put(NewsContract.DataContract.COLUMN_NAME_TAG, facade.getTag());
            byte[] image = EncodeImage(facade.getThumb());
            values.put(NewsContract.DataContract.COLUMN_NAME_THUMB, image);
            values.put(NewsContract.DataContract.COLUMN_NAME_TITLE, facade.getTitle());
            values.put(NewsContract.DataContract.COLUMN_NAME_WEBADDRESS, facade.getWebAddress());
            resolver.insert(uri, values);
        }
    }

}
