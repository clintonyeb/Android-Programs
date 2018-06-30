package com.example.clinton.light.dict_other;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.example.clinton.light.database.DictionaryContract;

import java.util.List;


public class StoreDictOther extends AsyncTask<Object, Void, Void> {
    Context context;
    ContentResolver resolver;
    List<DictOtherFacade> list;

    @Override
    protected Void doInBackground(Object... params) {
      context = (Context) params[0];
        resolver = context.getContentResolver();
        list = (List<DictOtherFacade>) params[1];
        DeleteData();
       StoreData();

        return null;
    }
    private void StoreData()
    {
        for (DictOtherFacade facade :
                list) {

            ContentValues values = new ContentValues();
            values.put(DictionaryContract.DictionaryDataContract.WORD, facade.getmWord());
            resolver.insert(DictionaryContract.CONTENT_URI_OTHER, values);
        }
    }
    private void DeleteData()
    {
        resolver.delete(DictionaryContract.CONTENT_URI_OTHER, null,null);
    }

}
