package com.example.clinton.companion.dictionary.workers;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.clinton.companion.dictionary.facades.DictionaryHelper;
import com.example.clinton.companion.utilities.FRAGMENT_ID;


public class DeleteFavWordAsync extends AsyncTask<Object, Void, Void> {

    long id;
    Context context;
    ContentResolver contentResolver;
    FRAGMENT_ID fragmentId;

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Deleted from Favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Object... params) {
        id = (long)params[0];
        context = (Context)params[1];
        fragmentId = (FRAGMENT_ID)params[2];
        contentResolver = context.getContentResolver();
        DeleteInFavTable();
        return null;
    }

    private void DeleteInFavTable() {
        int i = contentResolver.delete(DictionaryHelper.GetDictionaryUri(fragmentId), "_id='" + id + "'", null);
    }
}
