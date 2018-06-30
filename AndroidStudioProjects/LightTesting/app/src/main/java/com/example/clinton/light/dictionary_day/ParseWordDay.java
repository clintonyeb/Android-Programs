package com.example.clinton.light.dictionary_day;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ParseWordDay {
    public static WordDay ParseJSON(JSONObject jsonObject)
        {
            String wordName = "" ;
            String root = "";
            JSONArray examples;
            JSONArray definitions;
            String examp = "";
            String speech = "";
            String definition = "";
            WordDay wordDay = new WordDay();
            try
            {
                wordName = jsonObject.getString("word");
                root = jsonObject.getString("note");
                examples = jsonObject.getJSONArray("examples");
                definitions =jsonObject.getJSONArray("definitions");
                for(int i = 0; i < examples.length(); i++)
                {
                    JSONObject exampleObject = examples.getJSONObject(i);
                    if(i > 0)
                    {
                        examp = examp + "\n\n";
                    }
                    examp = examp +String.valueOf(i + 1) + ". " +  exampleObject.getString("text");
                }

                for(int i = 0; i < definitions.length(); i++)
                {
                    JSONObject obj = definitions.getJSONObject(i);
                    if(i > 0)
                    {
                        definition = definition + "\n\n";
                    }
                    definition = definition + String.valueOf(i + 1)+ ". "  +  obj.getString("text");
                    speech = obj.getString("partOfSpeech");
                }
                wordDay.setmWord(wordName);
                wordDay.setmRoot(root);
                wordDay.setmDefinition(definition);
                wordDay.setmExample(examp);
                wordDay.setmSpeech(speech);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return wordDay;
        }
}
