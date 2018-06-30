package com.example.clinton.light.dictionary_day;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.clinton.light.database.DictionaryContract;
import com.example.clinton.light.receivers.TodayWordReceiver;
import com.example.clinton.light.services.FetchTodayWordService;


public class StoreData
{
    ContentResolver dResolver;
    WordDay day;
    Context context;

    public Void DoWork (FetchTodayWordService service, Intent intent ) {
        day = service.wordDay;
        context = service.getApplicationContext();
        dResolver = context.getContentResolver();
        DeleteRows();
         if (InsertIntoTable())
         {
             WordNotify.notify(context,day.getmWord(), day.getmDefinition() );
         }
        TodayWordReceiver.completeWakefulIntent(intent);
        service.stopSelf();
        return null;
    }

    private void DeleteRows()
    {
        dResolver.delete(DictionaryContract.CONTENT_URI_TODAY, null, null);
    }

    private boolean InsertIntoTable() {
        ContentValues values = new ContentValues();
        values.put(DictionaryContract.DictionaryDataContract.WORD, day.getmWord());
        values.put(DictionaryContract.DictionaryDataContract.DEFINITION, day.getmDefinition());
        values.put(DictionaryContract.DictionaryDataContract.ROOT, day.getmRoot());
        values.put(DictionaryContract.DictionaryDataContract.SPEECH, day.getmSpeech());
        values.put(DictionaryContract.DictionaryDataContract.EXAMPLE, day.getmExample());

        Uri uri = dResolver.insert(DictionaryContract.CONTENT_URI_TODAY, values);
        Log.i("OUTPUT", "inserted at" + String.valueOf(uri));
        return true;
    }
}
