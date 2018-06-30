package com.example.clinton.light.dictionary_main;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.clinton.light.database.DictionaryContract;
import com.example.clinton.light.random_word.RandomFacade;

import java.util.List;


public class StoreRecentWord extends AsyncTask<Object, Void, Void> {

    Context context;
    List<RandomFacade> randomFacade;
    ContentResolver dResolver;
    @Override
    protected Void doInBackground (Object... params) {
        context = (Context)params[0];
        randomFacade = (List<RandomFacade>)params[1];
        dResolver = context.getContentResolver();
        Delete();
        InsertIntoTable();

        return null;
    }
    private boolean InsertIntoTable() {
        for (int i = 0; i < randomFacade.size(); i++)
        {
            RandomFacade facade = randomFacade.get(i);
            ContentValues values = new ContentValues();
            values.put(DictionaryContract.DictionaryDataContract.WORD, facade.getmWord());
            values.put(DictionaryContract.DictionaryDataContract.DEFINITION, facade.getmDefinition());
            values.put(DictionaryContract.DictionaryDataContract.EXAMPLE, facade.getmExamples());
            values.put(DictionaryContract.DictionaryDataContract.SPEECH, facade.getmSpeech());
            values.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, 0);
            values.put(DictionaryContract.DictionaryDataContract.WORD_ID , facade.getWordID());
            Uri uri = dResolver.insert(DictionaryContract.CONTENT_URI_DICTIONARY, values);
            if (i == 0)
            {
                RetrieveRecentData();
                dResolver.insert(DictionaryContract.CONTENT_URI_RECENT ,values);
            }
        }

        return true;

    }

    private void Delete()
    {
        dResolver.delete(DictionaryContract.CONTENT_URI_DICTIONARY, null, null);
    }
    private void RetrieveRecentData()
    {
        Cursor cursor = dResolver.query(DictionaryContract.CONTENT_URI_RECENT, null, null, null, null, null);
        if (cursor == null)
            return;
        if(cursor.getCount() > 9)
        {
            cursor.moveToFirst();
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            int del =  dResolver.delete(DictionaryContract.CONTENT_URI_RECENT, "_id="+id , null);
        }
        cursor.close();
    }

}
