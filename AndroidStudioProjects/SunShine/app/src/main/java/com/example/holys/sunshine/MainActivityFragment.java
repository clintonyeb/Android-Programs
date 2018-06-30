package com.example.holys.sunshine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static int units;
    public static ArrayAdapter mAdapter;
    private static final int REQUEST_CODE = 10;
    String LOCATION;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.forecast_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh)
        {
            CheckAndConnect();
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_map)
        {
            Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                    .appendQueryParameter("q", LOCATION).build();
            Intent intent = new Intent(Intent.ACTION_VIEW,  geoLocation);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null)
            {
                startActivityForResult(intent, REQUEST_CODE);
            }
            else
                Toast.makeText(getActivity(), "No installed component", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }


    private  boolean CheckConnectionState()
    {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info .isConnected())
        {
            return true;
        }
        else
            return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String[] list = new String[]{
        };
        List<String> arrayList = new ArrayList<String>(Arrays.asList(list));
        mAdapter = new ArrayAdapter(getActivity(),
                R.layout.list_item_forecast_textview,
                R.id.list_item_forecast_textview, arrayList );
        ListView listView = (ListView) rootView.findViewById(R.id.listveiw_forecast);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                String data = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, data);
                startActivity(intent);
            }
        });

        return rootView;

    }

    private void CheckAndConnect()
    {
        if (CheckConnectionState())
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String userPref = preferences.getString(SettingsActivity.LOCATION_SERVICE, String.valueOf(R.string.pref_key));
            LOCATION = userPref;
            String unitPref = preferences.getString("example_list", null);
            int val = Integer.parseInt(unitPref);
            if (val == 1)
            {
                units = 1;
            }
            else if(val == 2)
                units = 2 ;

            new GetForeCast().execute(userPref);
            //Toast.makeText(getActivity(), "Weather updated", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getActivity(), "You are offline", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart () {
        super.onStart();
        CheckAndConnect();
    }

}

  class GetForeCast extends AsyncTask<String, Void, String[]>
{


    @Override
    protected String[] doInBackground(String... pinCode) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String pin_code = pinCode[0];
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String BASEURI = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            Uri uriBuider = Uri.parse(BASEURI)
                    .buildUpon()
                    .appendQueryParameter("q", pin_code)
                    .appendQueryParameter("mode", "json")
                    .appendQueryParameter("units", "metric")
                    .appendQueryParameter("cnt", "7")
                    .appendQueryParameter("APPID", "ae7b9f219613aaa353ceefd304dceeed")
                    .build();

            URL url = new URL(uriBuider.toString());
            //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Bhutta&mode=json&units=metric&cnt=7&APPID=ae7b9f219613aaa353ceefd304dceeed");

            // Create the request to OpenWeatherMap, and open the connection
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
            String[] data = passer.getWeatherDataFromJson(forecastJsonStr, 7);
            return data;
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        }
        catch (JSONException e)
        {
            Log.e("Passing JSON", "Error ", e);
        }
        finally{
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
    protected void onPostExecute(String[] data)
    {
        if (data != null)
        {
            MainActivityFragment fragment = new MainActivityFragment();
            fragment.mAdapter.clear();
            fragment.mAdapter.addAll(data);
        }

    }
}