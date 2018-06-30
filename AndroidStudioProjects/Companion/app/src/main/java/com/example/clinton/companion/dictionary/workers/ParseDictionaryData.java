package com.example.clinton.companion.dictionary.workers;

import android.content.Context;

import com.example.clinton.companion.dictionary.facades.DictionaryFacade;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.FRAGMENT_ID;
import com.example.clinton.companion.utilities.HelperMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseDictionaryData {
    DictionaryFacade dictionaryFacade;

    public void ParseJSON(JSONObject JSON, Context context, FRAGMENT_ID fragmentId)
    {
        dictionaryFacade = new DictionaryFacade();
        try {
            JSONArray results = JSON.getJSONArray("results");
            if (results.length() < 1)
                return;
            for (int i = 0; i < results.length(); i++)
            {
                JSONObject result = results.getJSONObject(i);
                dictionaryFacade.setmWord(result.getString("headword"));
                try
                {
                    dictionaryFacade.setmSpeech(result.getString("part_of_speech"));
                }
                catch (JSONException e) {
                    dictionaryFacade.setmSpeech("");
                }

                try
                {
                    JSONArray senses = result.getJSONArray("senses");
                    JSONObject item = senses.getJSONObject(0);
                    JSONArray def = item.getJSONArray("definition");
                    dictionaryFacade.setmDefinition(def.getString(0));
                }
                catch (JSONException e) {
                    dictionaryFacade.setmDefinition("");
                }
                try
                {
                    JSONArray senses = result.getJSONArray("senses");
                    JSONObject item = senses.getJSONObject(0);
                    JSONArray exa = item.getJSONArray("examples");
                    JSONObject obj = exa.getJSONObject(0);
                    dictionaryFacade.setmExamples(obj.getString("text"));
                }
                catch (JSONException e) {
                    dictionaryFacade.setmExamples("");
                }
                dictionaryFacade.setmFavorited(0);

                StoreDictionaryData storeDictionaryData = new StoreDictionaryData(dictionaryFacade, context, fragmentId);
                if (i == 0)
                {
                    storeDictionaryData.DeleteData();
                    storeDictionaryData.InsertIntoRecent();
                }
                storeDictionaryData.InsertIntoTable();
                if (i == results.length() - 1)
                {
                    HelperMethods.sendBroadCast(NewsResults.Success, context);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
