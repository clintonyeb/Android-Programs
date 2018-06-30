package com.example.clinton.light.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.clinton.light.R;
import com.example.clinton.light.dictionary_day.WordDay;
import com.example.clinton.light.menuFragments.RetrieveData;

public class TodayWordActivity extends AppCompatActivity {

    TextView word;
    TextView root;
    TextView speech;
    TextView definition;
    TextView example;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dictionary);
        word = (TextView)findViewById(R.id.word);
        MainActivity.SetStrictMode();
        root = (TextView)findViewById(R.id.root);
        speech = (TextView)findViewById(R.id.speech);
        definition = (TextView)findViewById(R.id.meaning);
        example = (TextView)findViewById(R.id.exampleTextView);
        new RetrieveData().execute(getApplicationContext(), this);
    }
    public void DisplayData(WordDay wordDay)
    {
        if (wordDay == null)
            return;

        String wrd = "<u>"+wordDay.getmWord()+"</u>";
        word.setText(Html.fromHtml(wrd));
        String rt = "<i>" + wordDay.getmRoot()+"</i>";
        root.setText(Html.fromHtml(rt));
        String sp = "<i><b>" + wordDay.getmSpeech()+"</b></i>";
        speech.setText(Html.fromHtml(sp));
        definition.setText(wordDay.getmDefinition());
        example.setText(wordDay.getmExample());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
