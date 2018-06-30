package com.example.clinton.chatclientdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Connect connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect = new Connect();
        connect.execute();
    }

    public void Clicked(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);

        connect.sendMessage(editText.getText().toString());

    }
}
