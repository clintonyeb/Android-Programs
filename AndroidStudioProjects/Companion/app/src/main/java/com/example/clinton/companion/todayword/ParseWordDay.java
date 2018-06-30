package com.example.clinton.companion.todayword;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ParseWordDay {
    public static WordDay ParseJSON(JSONObject jsonObject)
        {
             String mWord;
             Definitions mDefinition;
             String mRoot;
             Examples mExample;

            WordDay wordDay = new WordDay();
            mExample = Examples.newInstance();
            mDefinition = Definitions.newInstance();
                try {
                    mWord = jsonObject.getString("word");
                }catch (JSONException e)
                {
                    mWord = "";
                }

                try
                {
                    mRoot = jsonObject.getString("note");
                }catch (JSONException e)
                {
                    mRoot = "";
                }
                try {

                    JSONArray examples = jsonObject.getJSONArray("examples");
                    for(int i = 0; i < examples.length(); i++)
                    {
                        JSONObject exampleObject = examples.getJSONObject(i);
                        String title = "";
                        try {
                            title = exampleObject.getString("title");
                        }catch (JSONException e)
                        {
                            title = "";
                        }

                        String text;
                        try {
                            text = exampleObject.getString("text");
                        }catch (JSONException e)
                        {
                            text = "";
                        }

                        String titleFormat = String.valueOf(i + 1) + ". " + title + ">";
                        mExample.setTitle(mExample.getTitle() + titleFormat);
                        mExample.setText(mExample.getText() + text + ">");

                    }
                }catch (JSONException e)
                {
                    mExample.setTitle(mExample.getTitle() + "" + ">");
                    mExample.setText(mExample.getText() + "" + ">");
                }

                try {
                    JSONArray definitions =jsonObject.getJSONArray("definitions");
                    for(int i = 0; i < definitions.length(); i++)
                    {
                        JSONObject defObject = definitions.getJSONObject(i);
                        String partOfSpeech = "";
                        try {
                            partOfSpeech = defObject.getString("partOfSpeech");
                        }catch (JSONException e)
                        {
                          partOfSpeech = "";
                        }
                        String definition = "";
                        try {
                           definition  = defObject.getString("text");
                        }catch (JSONException e)
                        {
                            definition = "";
                        }
                        String format = String.valueOf(i + 1) + ". " + partOfSpeech + ">";
                        mDefinition.setPartOfSpeech(mDefinition.getPartOfSpeech() + format);
                        mDefinition.setText(mDefinition.getText() + definition + ">");

                    }
                }catch (JSONException e)
                {
                   mDefinition.setText (mDefinition.getText()  + "" + ">");
                   mDefinition.setPartOfSpeech(mDefinition.getPartOfSpeech() + "" + ">");
                }
                wordDay.setmWord(mWord);
                wordDay.setmRoot(mRoot);
                wordDay.setmDefinition(mDefinition);
                wordDay.setmExample(mExample);
            return wordDay;
        }
}
