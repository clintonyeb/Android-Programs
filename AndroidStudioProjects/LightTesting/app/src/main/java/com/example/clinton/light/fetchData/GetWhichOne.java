package com.example.clinton.light.fetchData;

import android.net.Uri;

import com.example.clinton.light.database.NewsContract;
import com.example.clinton.light.tabs.TabsSuperClass;

import java.net.MalformedURLException;
import java.net.URL;


public class GetWhichOne {
    final static String baseUri = "http://content.guardianapis.com/search?";
    

    public static URL GetURL(int numb)
    {
        URL url = null;
        Uri uriBuilder;
        try
        {
        switch (numb)
        {
                case 1:

                    uriBuilder = Uri.parse(baseUri)
                            .buildUpon()
                            .appendQueryParameter("section", "culture|local|music|books|society")
                            .appendQueryParameter("order-by", "newest")
                            .appendQueryParameter("use-date", "published")
                            .appendQueryParameter("show-fields", "trailText,thumbnail")
                            .appendQueryParameter("page", String.valueOf(TabsSuperClass.pageNumber))
                            .appendQueryParameter("page-size", "10")
                            .appendQueryParameter("api-key", "dfd013c3-f8ba-424c-a5f3-c41a33aa975b")
                            .build();
                    url = new URL(uriBuilder.toString());
                    break;
            case 2:

                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter("section", "lifeandstyle|education|fashion|help")
                        .appendQueryParameter("order-by", "newest")
                        .appendQueryParameter("use-date", "published")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(TabsSuperClass.pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key", "164a0c25-b3a1-4751-b763-09e1ac0dbd45")
                        .build();
                url = new URL(uriBuilder.toString());
                break;
            case 3:
                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter("section", "science|environment|technology|business")
                        .appendQueryParameter("order-by", "newest")
                        .appendQueryParameter("use-date", "published")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(TabsSuperClass.pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key", "255d0959-a40e-4fb5-98ae-4d3914d5009a")
                        .build();
                url = new URL(uriBuilder.toString());
                break;
            case 4:
                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter("section", "sport|football")
                        .appendQueryParameter("order-by", "newest")
                        .appendQueryParameter("use-date", "published")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(TabsSuperClass.pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key", "255d0959-a40e-4fb5-98ae-4d3914d5009a")
                        .build();
                url = new URL(uriBuilder.toString());
                break;
            case 5:
                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter("section", "world|opinion|media|us-news|australia-news|uk-news")
                        .appendQueryParameter("order-by", "newest")
                        .appendQueryParameter("use-date", "published")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(TabsSuperClass.pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key", "164a0c25-b3a1-4751-b763-09e1ac0dbd45")
                        .build();
                url = new URL(uriBuilder.toString());
                break;
            case 6:
                String key = "relevance";
                switch (FetchNewsIntentService.POSITION)
                {
                    case 1:
                        key = "newest";
                        break;
                    case 2:
                        key = "relevance";
                        break;

                }
                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter("q", FetchNewsIntentService.TO_SEARCH)
                        .appendQueryParameter("order-by", key)
                        .appendQueryParameter("use-date", "published")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(TabsSuperClass.pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key", "8b02fa5d-9dab-42c8-88e6-4bb5f895e5ea")
                        .build();
                url = new URL(uriBuilder.toString());
                break;
            default:
                return null;
            }


        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }

    public static Uri GetContentURI(int numb)
    {
        switch (numb)
        {
            case 1:
                return NewsContract.CONTENT_URI_CULTURE;
            case 2:
                return NewsContract.CONTENT_URI_LIFESTYLE;
            case 3:
                return NewsContract.CONTENT_URI_SCIENCE;
            case 4:
                return NewsContract.CONTENT_URI_SPORT;
            case 5:
                return NewsContract.CONTENT_URI_WORLD;
            case 6:
                return NewsContract.CONTENT_URI_SEARCH;
            default:
                return null;

        }
    }
}
