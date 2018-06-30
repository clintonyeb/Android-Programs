package com.example.clinton.companion.dictionary.workers;

import android.content.Context;

import com.example.clinton.companion.dictionary.facades.RelatedFacade;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.HelperMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ParseRelativeWord {
    RelatedFacade facade;

    public void ParseJSON(JSONArray jsonArray, Context context)
    {
        int total = jsonArray.length();
        for (int i = 0; i < total; i ++)
        {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                String word = object.getString("word");
                word = String.valueOf(i + 1) + ". " + word;
                facade = new RelatedFacade();
                facade.setWord(word);

                StoreRelatedWord storeRelatedWord = new StoreRelatedWord(context, facade);
                if (i == 0)
                {
                    storeRelatedWord.DeleteData();
                }
                storeRelatedWord.StoreData();
                if (i == total - 1)
                {
                    HelperMethods.sendBroadCast(NewsResults.Success, context);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
