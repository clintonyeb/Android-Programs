package com.example.clinton.companion.todayword;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.clinton.companion.activities.TodayWordActivity;
import com.example.clinton.companion.database.DictionaryContract;
import com.example.clinton.companion.utilities.HelperMethods;

import java.util.Arrays;
import java.util.List;


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
             List<String> list =  Arrays.asList(day.getmDefinition().getText().split("\\s*>\\s*"));
             WordNotify.notify(context,day.getmWord(), list.get(0));
         }
        TodayWordReceiver.completeWakefulIntent(intent);
        return null;
    }

    private void DeleteRows()
    {
        dResolver.delete(DictionaryContract.CONTENT_URI_TODAY, null, null);
    }

    private boolean InsertIntoTable() {
        ContentValues values = new ContentValues();
        values.put(DictionaryContract.DictionaryDataContract.WORD, day.getmWord());
        values.put(DictionaryContract.DictionaryDataContract.DEFINITION_TEXT, day.getmDefinition().getText());
        values.put(DictionaryContract.DictionaryDataContract.DEFINITION_SPEECH, day.getmDefinition().getPartOfSpeech());
        values.put(DictionaryContract.DictionaryDataContract.ROOT, day.getmRoot());
        values.put(DictionaryContract.DictionaryDataContract.EXAMPLE_TITLE, day.getmExample().getTitle());
        values.put(DictionaryContract.DictionaryDataContract.EXAMPLE_TEXT, day.getmExample().getText());

        Uri uri = dResolver.insert(DictionaryContract.CONTENT_URI_TODAY, values);
        HelperMethods.UpdateDate(TodayWordActivity.TODAY_WORD_ID, context);
        Log.i("OUTPUT", "inserted at" + String.valueOf(uri));
        return true;
    }
}
