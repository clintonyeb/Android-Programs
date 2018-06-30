package com.example.holys.light;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

public class LogInActivty extends AppCompatActivity {

    CheckBox box;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_activty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.login_text);
        box = (CheckBox)findViewById(R.id.myCheckBox);

        SharedPreferences pref = this.getSharedPreferences(getResources().getString(R.string.LOGGEDIN),MODE_PRIVATE );
        boolean status = pref.getBoolean
                (getResources().getString(R.string.LOGGEDIN), false);

        if (status)
        {
            Navigate();
        }
    }
    public void loginButton_Click(View view)
    {
        if (true)
        {
            SharedPreferences pref = this.getSharedPreferences(getResources().getString(R.string.LOGGEDIN),MODE_PRIVATE );
            SharedPreferences.Editor editor = pref.edit();
            if (box.isChecked())
            {
                editor.putBoolean(getResources().getString(R.string.LOGGEDIN), true);
            }
            else
            {
                editor.putBoolean(getResources().getString(R.string.LOGGEDIN), false);
            }

            editor.apply();
            Navigate();
            return;
        }
    }

    private void Navigate()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
