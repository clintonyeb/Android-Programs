package com.example.clinton.companion.news.workers;

import android.net.Uri;

import com.example.clinton.companion.utilities.API_KEYS;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

import java.net.MalformedURLException;
import java.net.URL;


public class GetWhichOne {
    final static String baseUri = "https://content.guardianapis.com/search?";
    

    public static URL GetURL(FRAGMENT_ID numb, int pageNumber)
    {
        Uri uriBuilder;
        try
        {

        switch (numb)
        {
                case CULTURE_ID:
                    uriBuilder = Uri.parse(baseUri)
                            .buildUpon()
                            .appendQueryParameter("section", "culture|local|music|books|society|" +
                                    "artanddesign|culture-network|culture-professional-network|extra")
                            .appendQueryParameter("order-by", "newest")
                            .appendQueryParameter("use-date", "published")
                            .appendQueryParameter("show-fields", "trailText,thumbnail")
                            .appendQueryParameter("page", String.valueOf(pageNumber))
                            .appendQueryParameter("page-size", "10")
                            .appendQueryParameter("api-key", API_KEYS.GUARDIAN_API_1 )
                            .build();
                    return new URL(uriBuilder.toString());
            case LIFESTYLE_ID:

                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter("section", "lifeandstyle|education|fashion")
                        .appendQueryParameter("order-by", "newest")
                        .appendQueryParameter("use-date", "published")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key", API_KEYS.GUARDIAN_API_2 )
                        .build();
                return new URL(uriBuilder.toString());
            case SCIENCE_ID:
                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter("section", "science|environment|technology|business|" +
                                "better-business|enterprise-network|money|tv-and-radio")
                        .appendQueryParameter("order-by", "newest")
                        .appendQueryParameter("use-date", "published")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key",API_KEYS.GUARDIAN_API_3 )
                        .build();
                return new URL(uriBuilder.toString());

            case SPORT_ID:
                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter("section", "sport|football")
                        .appendQueryParameter("order-by", "newest")
                        .appendQueryParameter("use-date", "published")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key", API_KEYS.GUARDIAN_API_4)
                        .build();
                return new URL(uriBuilder.toString());

            case WORLD_ID:
                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter
                                ("section", "world|opinion|media|us-news|" +
                                        "australia-news|uk-news|cities|community|" +
                                        "global-development|law|news|politics|media|media-network")
                        .appendQueryParameter("order-by", "newest")
                        .appendQueryParameter("use-date", "published")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key",API_KEYS.GUARDIAN_API_5 )
                        .build();
                return new URL(uriBuilder.toString());

            /*case NEWS_SEARCH_FRAGMENT_ID:
                String key = "relevance";
                uriBuilder = Uri.parse(baseUri)
                        .buildUpon()
                        .appendQueryParameter("q", FetchNewsData.searchQuery)
                        .appendQueryParameter("order-by", key)
                        .appendQueryParameter("use-date", "last-modified")
                        .appendQueryParameter("show-fields", "trailText,thumbnail")
                        .appendQueryParameter("page", String.valueOf(pageNumber))
                        .appendQueryParameter("page-size", "10")
                        .appendQueryParameter("api-key", )
                        .build();
                url = new URL(uriBuilder.toString());
                break;*/
            default:
                throw new IllegalArgumentException();
            }


        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
