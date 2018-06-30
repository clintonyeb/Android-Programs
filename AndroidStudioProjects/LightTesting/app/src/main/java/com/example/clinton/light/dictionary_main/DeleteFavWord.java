package com.example.clinton.light.dictionary_main;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.clinton.light.database.DictionaryContract;
import com.example.clinton.light.random_word.RandomFacade;

public class DeleteFavWord extends AsyncTask <Object, Void, Void>{

	Context context;
	ContentResolver dResolver;
    int which = -1;

	@Override
	protected void onPostExecute (Void aVoid) {
		super.onPostExecute(aVoid);
		Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected Void doInBackground(Object... params) {
		context = (Context)params[0];
		dResolver = context.getContentResolver();
        which = (int)params[2];
		DelFavWord(RetrieveWord((int) params[1]));
        UpdateWordTable((int)params[1]);
		return null;
	}

	private void DelFavWord(RandomFacade facade)
	{
        Cursor cursor = dResolver.query(DictionaryContract.CONTENT_URI_FAVORITES, null,
                DictionaryContract.DictionaryDataContract.WORD_ID + "='" + facade.getWordID() + "'", null,
                null, null);
        if (cursor == null)
            return;
        cursor.moveToFirst();
        Log.i("KEY", " " +cursor.getInt(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.WORD_ID )));
        int key =  cursor.getInt(cursor.getColumnIndex(DictionaryContract.DictionaryDataContract.WORD_ID ));

        dResolver.delete(DictionaryContract.CONTENT_URI_FAVORITES, "wordid='"+key+"'" , null);
        cursor.close();
	}
	private RandomFacade RetrieveWord(int position)
	{
        Cursor cursor = null;
        RandomFacade facade = null;
        switch (which)
        {
            case 0:
                 cursor = dResolver.query(DictionaryContract.CONTENT_URI_DICTIONARY, null, null, null, null, null);
                if(cursor == null || cursor.getCount() < 1)
                    return null;
                cursor.move(position + 1);
                facade = RandomFacade.fromCursor(cursor);
                cursor.close();
                break;
            case 1:
                cursor = dResolver.query(DictionaryContract.CONTENT_URI_RANDOM, null, null, null, null, null);
                if(cursor == null || cursor.getCount() < 1)
                    return null;
                cursor.move(position + 1);
                facade = RandomFacade.fromCursor(cursor);
                cursor.close();
                break;
            case 2:
                cursor = dResolver.query(DictionaryContract.CONTENT_URI_FAVORITES, null, null, null, null, null);
                if(cursor == null || cursor.getCount() < 1)
                    return null;
                cursor.move(position + 1);
                facade = RandomFacade.fromCursor(cursor);
                cursor.close();
                break;
            case 3:
                cursor = dResolver.query(DictionaryContract.CONTENT_URI_RECENT, null, null, null, null, null);
                if(cursor == null || cursor.getCount() < 1)
                    return null;
                cursor.move(position + 1);
                facade = RandomFacade.fromCursor(cursor);
                cursor.close();
                break;
        }
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
                    args.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, 0);
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
                    args.put(DictionaryContract.DictionaryDataContract.REC_FAVORITED, 0);
                    dResolver.update(DictionaryContract.CONTENT_URI_RANDOM, args, filter, null);
                    cursor.close();
                }
                break;
        }
        return true;
    }
}
