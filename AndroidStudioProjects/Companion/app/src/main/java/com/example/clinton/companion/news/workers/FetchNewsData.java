package com.example.clinton.companion.news.workers;

/*
public class FetchNewsData extends IntentService {

    public static final String FETCH_NEWS = "com.example.clinton.companion.news.action.FETCH_NEWS";
    public static final String NEWS_OBJECT = "com.example.clinton.companion.news.extra.NEWS_OBJECT";
    public static final String SERVICE_RESULT = "service_result";
    public static String searchQuery;
    NewsFeatures newsFeatures;


    public FetchNewsData() {
        super("FetchNewsData");
    }

    public static void startNewsFetch(Context context, NewsFeatures news) {
        Intent intent = new Intent(context, FetchNewsData.class);
        intent.setAction(FETCH_NEWS);
        intent.putExtra(NEWS_OBJECT, news);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (FETCH_NEWS.equals(action)) {
                newsFeatures = (NewsFeatures) intent.getSerializableExtra(NEWS_OBJECT);
                searchQuery = newsFeatures.getQuery();

                handleFetchingNews(String.valueOf(GetWhichOne.GetURL(newsFeatures.getFragmentID(),
                        newsFeatures.getPageNumber())));
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    private void handleFetchingNews(String url) {
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        new HandleNewsData().execute(response, getApplicationContext(), newsFeatures);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(getApplicationContext(), "Network too slow!", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Check your network!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                        Intent registrationComplete = new Intent(QuickstartPreferences.SERVICE_COMPLETE);
                        registrationComplete.putExtra(SERVICE_RESULT, false);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(registrationComplete);

                    }
                }
                );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

}
*/
