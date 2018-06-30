package com.example.clinton.companion.dictionary.workers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.clinton.companion.database.DictionaryContract;
import com.example.clinton.companion.dictionary.facades.DictionaryHelper;
import com.example.clinton.companion.dictionary.facades.DictionaryFacade;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

public class StoreDictionaryData {

    ContentResolver contentResolver;
    DictionaryFacade facade;
    FRAGMENT_ID fragmentID;

    public StoreDictionaryData(DictionaryFacade fac, Context context, FRAGMENT_ID fragmentId)
    {
        contentResolver = context.getContentResolver();
        facade = fac;
        fragmentID = fragmentId;
    }
    public void InsertIntoTable()
    {
        ContentValues values = new ContentValues();
        values.put(DictionaryContract.DictionaryDataContract.WORD, facade.getmWord());
        values.put(DictionaryContract.DictionaryDataContract.DEFINITION_TEXT, facade.getmDefinition());
        values.put(DictionaryContract.DictionaryDataContract.EXAMPLE_TEXT, facade.getmExamples());
        values.put(DictionaryContract.DictionaryDataContract.SPEECH, facade.getmSpeech());
        values.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, facade.getmFavorited());
        Uri uri = contentResolver.insert(DictionaryHelper.GetDictionaryUri(fragmentID), values);
    }

    public void InsertIntoRecent()
    {
        if (fragmentID == FRAGMENT_ID.MAIN_DICTIONARY_ID)
        {
            Cursor cursor = contentResolver.query(DictionaryContract.CONTENT_URI_RECENT, null, null, null, null);
            if (cursor != null && cursor.getCount() > 9) {
                cursor.moveToFirst();
                long id = cursor.getLong(cursor.getColumnIndex("_id"));
                contentResolver.delete(DictionaryContract.CONTENT_URI_RECENT, "_id="+id , null);
            }
            if (cursor != null) {
                cursor.close();
            }
            ContentValues values = new ContentValues();
            values.put(DictionaryContract.DictionaryDataContract.WORD, facade.getmWord());
            values.put(DictionaryContract.DictionaryDataContract.DEFINITION_TEXT, facade.getmDefinition());
            values.put(DictionaryContract.DictionaryDataContract.EXAMPLE_TEXT, facade.getmExamples());
            values.put(DictionaryContract.DictionaryDataContract.SPEECH, facade.getmSpeech());
            values.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, facade.getmFavorited());
            Uri uri = contentResolver.insert(DictionaryContract.CONTENT_URI_RECENT, values);
        }

    }
    public void DeleteData()
    {
        contentResolver.delete(DictionaryHelper.GetDictionaryUri(fragmentID), null,null);
    }
}
