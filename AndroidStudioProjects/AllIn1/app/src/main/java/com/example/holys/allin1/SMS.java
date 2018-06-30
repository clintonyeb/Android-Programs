package com.example.holys.allin1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SMS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
    }

    public void sms_onClick(View view)
    {
        EditText address = (EditText)findViewById(R.id.sms_addressEdit);
        String add = address.getText().toString();
        EditText body = (EditText)findViewById(R.id.sms_bodyEdit);
        String bo = body.getText().toString();
        Uri smsUri = Uri.parse("tel:" + add);
        Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
        if (add.length() > 0)
            intent.putExtra("address", add);
        if (bo.length() > 0)
            intent.putExtra("sms_body", bo);
        intent.setType("vnd.android-dir/mms-sms");
        startActivity(intent);
    }
}
