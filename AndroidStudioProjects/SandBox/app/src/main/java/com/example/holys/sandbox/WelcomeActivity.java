package com.example.holys.sandbox;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final CheckBox box = (CheckBox)findViewById(R.id.checkBox);
        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(WelcomeActivity.this);
                if (box.isChecked())
                {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("skipWelcome", true);
                    editor.commit();
                }
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        });
    }
}
