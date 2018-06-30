package com.example.clinton.companion.dictionary.workers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.clinton.companion.database.DictionaryContract;
import com.example.clinton.companion.dictionary.facades.RelatedFacade;


public class StoreRelatedWord {
    Context context;
    ContentResolver resolver;
    RelatedFacade facade;

    protected StoreRelatedWord(Context context, RelatedFacade facade) {
        this.context = context;
        resolver = context.getContentResolver();
        this.facade = facade;
    }
    public void StoreData()
    {
        ContentValues values = new ContentValues();
        values.put(DictionaryContract.DictionaryDataContract.WORD, facade.getWord());
        Uri uri =  resolver.insert(DictionaryContract.CONTENT_URI_RELATED, values);
    }
    public void DeleteData()
    {
        resolver.delete(DictionaryContract.CONTENT_URI_RELATED, null,null);
    }

}
