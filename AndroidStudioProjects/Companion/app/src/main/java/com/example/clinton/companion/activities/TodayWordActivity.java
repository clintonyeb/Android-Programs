package com.example.clinton.companion.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.clinton.companion.R;
import com.example.clinton.companion.todayword.CustomRecyAdapter;
import com.example.clinton.companion.todayword.RetrieveData;
import com.example.clinton.companion.todayword.WordDay;

public class TodayWordActivity extends AppCompatActivity {

    public static String TODAY_WORD_ID = "today_word";
    TextView word;
    TextView root;
    TextView speech;
    TextView definition;
    TextView example;
    RecyclerView defRecyclerView;
    RecyclerView exampRecyclerView;
    CustomRecyAdapter DefAdapter;
    CustomRecyAdapter ExampAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_activity);
        InititializeViews();
        SetAdapters();
    }

    private void InititializeViews()
    {
        word = (TextView)findViewById(R.id.word);
        root = (TextView)findViewById(R.id.root);
        speech = (TextView)findViewById(R.id.speech);
        definition = (TextView)findViewById(R.id.meaning);
        example = (TextView)findViewById(R.id.exampleTextView);
        defRecyclerView = (RecyclerView)findViewById(R.id.definitionRecView);
        exampRecyclerView = (RecyclerView)findViewById(R.id.exampleRecView);
        defRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exampRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        defRecyclerView.setNestedScrollingEnabled(false);
        exampRecyclerView.setNestedScrollingEnabled(false);
        new RetrieveData().execute(getApplicationContext(), this);
    }

    private void SetAdapters()
    {
        DefAdapter = new CustomRecyAdapter("", "");
        ExampAdapter = new CustomRecyAdapter("", "");
        defRecyclerView.setAdapter(DefAdapter);
        exampRecyclerView.setAdapter(ExampAdapter);
    }

    public void DisplayData(WordDay wordDay)
    {
        if (wordDay == null)
            return;

        String wrd = "<u><b>"+wordDay.getmWord()+"</b></u>";
        word.setText(Html.fromHtml(wrd));
        String rt = "<i>" + wordDay.getmRoot()+"</i>";
        root.setText(Html.fromHtml(rt));
        DefAdapter.upDateList(wordDay.getmDefinition().getPartOfSpeech(), wordDay.getmDefinition().getText());
        ExampAdapter.upDateList(wordDay.getmExample().getTitle(), wordDay.getmExample().getText());
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
