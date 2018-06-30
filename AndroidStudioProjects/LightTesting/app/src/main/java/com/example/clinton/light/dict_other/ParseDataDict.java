package com.example.clinton.light.dict_other;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ParseDataDict {
    private String word;
    List<DictOtherFacade> facadeList;

    public List<DictOtherFacade> ParseJSON(JSONArray jsonArray)
    {
        facadeList = new ArrayList();
        int total = jsonArray.length();
        for (int i = 0; i < total; i ++)
        {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                word = object.getString("word");
                word = String.valueOf(i + 1) + ". " + word;
                DictOtherFacade facade = new DictOtherFacade();
                facade.setmWord(word);
                facadeList.add(facade);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return facadeList;
    }

}
