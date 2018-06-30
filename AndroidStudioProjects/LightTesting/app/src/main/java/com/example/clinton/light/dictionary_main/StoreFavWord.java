package com.example.clinton.light.dictionary_main;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.clinton.light.database.DictionaryContract;
import com.example.clinton.light.random_word.RandomFacade;

public class StoreFavWord extends AsyncTask<Object, Void, Void> {
    Context context;
    ContentResolver dResolver;
    int which = -1;

	@Override
	protected void onPostExecute (Void aVoid) {
		super.onPostExecute(aVoid);
		Toast.makeText(context, "Saved to Favorites", Toast.LENGTH_SHORT).show();
	}

    @Override
    protected Void doInBackground (Object... params) {
        context = (Context)params[0];
        which = (int)params[2];
        dResolver = context.getContentResolver();
        UpdateWordTable((int)params[1]);
        RandomFacade facade = RetrieveWord((int)params[1]);
        InsertIntoTable(facade);
        return null;
    }


    private boolean InsertIntoTable(RandomFacade day) {
        ContentValues values = new ContentValues();
        values.put(DictionaryContract.DictionaryDataContract.WORD, day.getmWord());
        values.put(DictionaryContract.DictionaryDataContract.DEFINITION, day.getmDefinition());
        values.put(DictionaryContract.DictionaryDataContract.EXAMPLE, day.getmExamples());
        values.put(DictionaryContract.DictionaryDataContract.SPEECH, day.getmSpeech());
        values.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, day.getmFavorited());
        values.put(DictionaryContract.DictionaryDataContract.WORD_ID, day.getWordID());
        Log.i("word id", " " +day.getWordID());
        Uri uri = dResolver.insert(DictionaryContract.CONTENT_URI_FAVORITES, values);
        Log.i("OUTPUT", "favorites inserted " + uri);
        return true;
    }

    private RandomFacade RetrieveWord(int position)
    {
        Cursor cursor = null;
        switch (which)
        {
            case 0:
                cursor = dResolver.query(DictionaryContract.CONTENT_URI_DICTIONARY, null, null, null, null, null);
                break;
            case 1:
                cursor = dResolver.query(DictionaryContract.CONTENT_URI_RANDOM, null, null, null, null, null);
                break;
            case 2:
                break;
            case 3:
                cursor = dResolver.query(DictionaryContract.CONTENT_URI_RECENT, null, null, null, null, null);
                break;
        }

        if(cursor == null || cursor.getCount() < 1)
            return null;
        cursor.move(position + 1);
        RandomFacade facade = RandomFacade.fromCursor(cursor);
        cursor.close();
        return facade;
    }


    private boolean UpdateWordTable(int position)
    {
        Cursor cursor = null;
        switch (which)
        {
            case 0:
                cursor = dResolver.query(DictionaryContract.CONTENT_URI_DICTIONARY, null, null, null, null, null);
                if (cursor != null) {
                    cursor.move(position + 1);
                    Long id = cursor.getLong(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract._ID));
                    Log.i("Update", " " + id);
                    String filter = DictionaryContract.DictionaryDataContract._ID + "=" + id;
                    ContentValues args = new ContentValues();
                    args.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, 1);
                        dResolver.update(DictionaryContract.CONTENT_URI_DICTIONARY, args, filter, null);
                    cursor.close();
                }
                break;
            case 1:
                cursor = dResolver.query(DictionaryContract.CONTENT_URI_RANDOM, null, null, null, null, null);
                if (cursor != null) {
                    cursor.move(position + 1);
                    Long id = cursor.getLong(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract._ID));
                    Log.i("Update", " " + id);
                    String filter = DictionaryContract.DictionaryDataContract._ID + "=" + id;
                    ContentValues args = new ContentValues();
                    args.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, 1);
                    dResolver.update(DictionaryContract.CONTENT_URI_RANDOM, args, filter, null);
                    cursor.close();
                }
                break;
        }

        return true;
    }
}