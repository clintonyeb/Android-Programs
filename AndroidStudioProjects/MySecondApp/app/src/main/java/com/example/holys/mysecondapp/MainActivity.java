package com.example.holys.mysecondapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.orientation);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.horizontal:
                        group.setOrientation(LinearLayout.HORIZONTAL);
                        break;
                    case R.id.vertical:
                        group.setOrientation(LinearLayout.VERTICAL);
                        break;
                }
            }
        });
    }

    public void onClick(View view)
    {

        EditText editText = (EditText)findViewById(R.id.main_input);
        String message = editText.getText().toString();
        if (message.length() != 0)
        {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Type something dear", Toast.LENGTH_LONG).show();

    }
}
