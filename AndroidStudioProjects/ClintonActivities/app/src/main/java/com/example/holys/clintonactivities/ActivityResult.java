package com.example.holys.clintonactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_result);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
            return;
        String value = extras.getString(Intent.EXTRA_TEXT);
        TextView view = (TextView) findViewById(R.id.displayIntentExtra);
        if (value != null) {
            view.setText(value);
        } else {
            view.setText("Didnt receive data");
        }
    }

    @Override
    public  void finish()
    {
        Intent intent = new Intent();
        EditText text = (EditText)findViewById(R.id.returnValue);
        String mess = text.getText().toString();
        intent.putExtra("reply", mess);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
