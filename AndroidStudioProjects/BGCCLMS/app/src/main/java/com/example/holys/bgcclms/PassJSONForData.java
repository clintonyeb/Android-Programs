package com.example.holys.bgcclms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by holys on 2/17/2016.
 */
public class PassJSONForData
 {
    final static String STATUS = "status";
    final static String ALL_RESULT = "result";
    final static String DOCUMENTATION = "docs";
    final static String DATA = "enriched";
    final static String mURL = "url";
    final static String PUBLICATION_DATE = "publicationDate";
     final static String SOURCE = "source";
     List<NewsDataFacade> newsFeed;

     public List<NewsDataFacade> PassJSON(String JSON) throws JSONException
    {
        String imageUrl;
        String date;
        String text;
        String title;
        String webAddress;

        newsFeed = new ArrayList<>();
        JSONObject JSONReceived = new JSONObject(JSON);
        String status = JSONReceived.getString(STATUS);
        if(!status.equalsIgnoreCase("OK"))
        {
            return null;

        }
        JSONObject result = JSONReceived.getJSONObject(ALL_RESULT);
        JSONArray jsonArray = result.getJSONArray(DOCUMENTATION);

        //int len = jsonArray.length();
        //Log.v("Array Length",String.valueOf(len));
        //mResult = new String[5][5];
        for (int i = 0; i < 5; i++)
        {
            //newsData[i] = new NewsDataFacade();
            JSONObject firstData = jsonArray.getJSONObject(i);
            JSONObject source = firstData.getJSONObject(SOURCE);
            JSONObject enriched = source.getJSONObject(DATA);
            JSONObject uRL = enriched.getJSONObject(mURL);
            //imageUrl = uRL.getString("image");
            //Bitmap bitmap = getImage(imageUrl);
            JSONObject pubDate = uRL.getJSONObject(PUBLICATION_DATE);
            date = pubDate.getString("date");
            date = ParseDate(date);
            text =  uRL.getString("text");
            title = uRL.getString("title");
            webAddress = uRL.getString("url");
            newsFeed.add(new NewsDataFacade(webAddress, text, date, title));

        }

        return newsFeed;
    }

     private String ParseDate(String date)
     {
          String[] s = date.split("T");
         String d = s[1];
         d = d.substring(0,4);
         int val = Integer.parseInt(d);
         if (val == 0)
         {
             return "Just Now";
         }
         else
         {
             return "Time: " + getDate(val);
         }
     }

     private String getDate(int date)
     {
         SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
         try
         {
             java.util.Date d = sdf.parse(Integer.toString(date));
             return new SimpleDateFormat("HH:mm").format(d);
         }catch(Exception e)
         {
             System.out.println("Couldnt format date");
         }
         return String.valueOf(date);
     }

     /*private Bitmap getImage(String uri)
     {
         Bitmap bmp = null;
         try
         {
             URL url = new URL(uri);
             bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
             //Log.v("Here", "I am here");
         }
         catch (MalformedURLException e)
         {

         }
         catch (IOException e)
         {

         }
         return bmp;
     }*/
}
