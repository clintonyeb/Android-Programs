package com.example.clinton.light.random_word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParseRandomWord {
    List<RandomFacade> facadeList = null;

    public List<RandomFacade> ParseJSON(JSONObject JSON)
    {
        facadeList = new ArrayList<>();
        try {
            JSONArray results = JSON.getJSONArray("results");
            if (results.length() < 1)
                return null;
            for (int i = 0; i < results.length(); i++)
            {
                JSONObject result = results.getJSONObject(i);
                RandomFacade facade = new RandomFacade();
                facade.setmWord(result.getString("headword"));
                try
                {
                    facade.setmSpeech(result.getString("part_of_speech"));
                }
                catch (JSONException e) {
                    facade.setmSpeech("");
                }

                try
                {
                    JSONArray senses = result.getJSONArray("senses");
                    JSONObject item = senses.getJSONObject(0);
                    JSONArray def = item.getJSONArray("definition");
                    facade.setmDefinition(def.getString(0));
                }
                catch (JSONException e) {
                    facade.setmDefinition("");
                }
                try
                {
                    JSONArray senses = result.getJSONArray("senses");
                    JSONObject item = senses.getJSONObject(0);
                    JSONArray exa = item.getJSONArray("examples");
                    JSONObject obj = exa.getJSONObject(0);
                    facade.setmExamples(obj.getString("text"));
                }
                catch (JSONException e) {
                    facade.setmExamples("");
                }
                facade.setmFavorited(0);
                facade.setWordID(new Random().nextInt(100000));

                facadeList.add(facade);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        //Collections.reverse(facadeList);
        return facadeList;
    }

}
