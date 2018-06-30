package com.example.clinton.companion.dictionary.facades;

import android.net.Uri;

import com.example.clinton.companion.database.DictionaryContract;
import com.example.clinton.companion.database.NewsContract;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

public class DictionaryHelper {


    public static Uri GetDictionaryUri(FRAGMENT_ID id)
    {
        switch (id)
        {
            case MAIN_DICTIONARY_ID:
                return DictionaryContract.CONTENT_URI_DICTIONARY;
            case RANDOM_WORD_ID:
                return DictionaryContract.CONTENT_URI_RANDOM;
            case RELATED_ID:
                return DictionaryContract.CONTENT_URI_RELATED;
            case DICTIONARY_FAVORITE_ID:
                return DictionaryContract.CONTENT_URI_FAVORITES;
            case DICTIONARY_RECENT_ID:
                return DictionaryContract.CONTENT_URI_RECENT;
            case DICTIONARY_HOME:
                return DictionaryContract.CONTENT_URI_DICTIONARY;
            case RANDOM_HOME:
                return DictionaryContract.CONTENT_URI_RANDOM;
            case QUOTE_ID:
                return NewsContract.CONTENT_URI_QUOTE;
            default:
                throw new IllegalArgumentException("Unknown URI " + id);
        }
    }


}
