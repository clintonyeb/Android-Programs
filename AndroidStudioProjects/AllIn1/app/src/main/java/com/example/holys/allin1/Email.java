package com.example.holys.allin1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Email extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
    }

    public void onClick(View view)
    {
        EditText address = (EditText)findViewById(R.id.addressEdit);
        String add = address.getText().toString();
        EditText subject = (EditText)findViewById(R.id.subjectEdit);
        String sub = subject.getText().toString();
        EditText body = (EditText)findViewById(R.id.bodyEdit);
        String bo = body.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        if (add.length() > 0)
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {add });
        if (sub.length() > 0)
            intent.putExtra(Intent.EXTRA_SUBJECT, sub);
        if (bo.length() > 0)
            intent.putExtra(Intent.EXTRA_TEXT, bo);
        startActivity(Intent.createChooser(intent, ""));
    }
}
