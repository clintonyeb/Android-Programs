package com.example.holys.clintonactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button_Start_onClick(View view)
    {

        EditText text = (EditText) findViewById(R.id.inputForIntent);
        String textMessage = text.getText().toString();
        if (textMessage.length() == 0)
            return;
        Intent intent = new Intent(this, ActivityResult.class);
        intent.putExtra(Intent.EXTRA_TEXT, textMessage);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK && requestCode == 10)
        {
            if (data.hasExtra("reply"))
            {
                String result = data.getExtras().getString("reply");
                if (result != null && result.length() > 0)
                {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No reply", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}
