package com.example.clinton.companion.news.workers;

import android.net.Uri;

import com.example.clinton.companion.database.NewsContract;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

public class GetUri {
    public static Uri ThisURI(FRAGMENT_ID id)
    {
        switch (id)
        {
            case CULTURE_ID:
                return NewsContract.CONTENT_URI_CULTURE;
            case LIFESTYLE_ID:
                return NewsContract.CONTENT_URI_LIFESTYLE;
            case SCIENCE_ID:
                return NewsContract.CONTENT_URI_SCIENCE;
            case SPORT_ID:
                return NewsContract.CONTENT_URI_SPORT;
            case WORLD_ID:
                return NewsContract.CONTENT_URI_WORLD;
            case NEWS_SEARCH_FRAGMENT_ID:
                return NewsContract.CONTENT_URI_SEARCH;
            case NEWS_FAVORITE_FRAG_ID:
                return NewsContract.CONTENT_URI_FAVORITE;
            case NEWS_HOME_CULTURE:
                return NewsContract.CONTENT_URI_CULTURE;
            case NEWS_HOME_LIFE:
                return NewsContract.CONTENT_URI_LIFESTYLE;
            case NEWS_HOME_SCIENCE:
                return NewsContract.CONTENT_URI_SCIENCE;
            case NEWS_HOME_SPORT:
                return NewsContract.CONTENT_URI_SPORT;
            case NEWS_HOME_WORLD:
                return NewsContract.CONTENT_URI_WORLD;
            default:
                throw new IllegalArgumentException("Unknown URI " + id);
        }
    }
}
