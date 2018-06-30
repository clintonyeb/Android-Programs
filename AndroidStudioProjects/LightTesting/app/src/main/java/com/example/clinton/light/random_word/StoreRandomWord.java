package com.example.clinton.light.random_word;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.clinton.light.database.DictionaryContract;

import java.util.List;

public class StoreRandomWord extends AsyncTask<Object, Void, Void> {

    Context context;
    List<RandomFacade> randomFacade;
    ContentResolver dResolver;
    @Override
    protected Void doInBackground (Object... params) {
        context = (Context)params[0];
        randomFacade = (List<RandomFacade>)params[1];
        dResolver = context.getContentResolver();
        DeleteData();
        InsertIntoTable();

        return null;
    }
    private boolean InsertIntoTable() {
        for (RandomFacade facade :
                randomFacade) {
            ContentValues values = new ContentValues();
            values.put(DictionaryContract.DictionaryDataContract.WORD, facade.getmWord());
            values.put(DictionaryContract.DictionaryDataContract.DEFINITION, facade.getmDefinition());
            values.put(DictionaryContract.DictionaryDataContract.EXAMPLE, facade.getmExamples());
            values.put(DictionaryContract.DictionaryDataContract.SPEECH, facade.getmSpeech());
            values.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, facade.getmFavorited());
            values.put(DictionaryContract.DictionaryDataContract.WORD_ID, facade.getWordID());
            Uri uri = dResolver.insert(DictionaryContract.CONTENT_URI_RANDOM, values);
        }
        return true;

    }
    private void DeleteData()
    {
        dResolver.delete(DictionaryContract.CONTENT_URI_RANDOM, null,null);
    }
}
