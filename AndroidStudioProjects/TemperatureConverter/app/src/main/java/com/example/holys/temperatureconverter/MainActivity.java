package com.example.holys.temperatureconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button1:
                RadioButton celButton = (RadioButton) findViewById(R.id.radio0);
                RadioButton fahButton = (RadioButton) findViewById(R.id.radio1);
                EditText text = (EditText)findViewById(R.id.inputValue);
                TextView textView = (TextView) findViewById(R.id.textView);
                if (text.getText().length() == 0)
                {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
                    return;
                }

                float inputValue = Float.parseFloat(text.getText().toString());
                if (celButton.isChecked())
                {
                    textView.setText(String.valueOf(DoConversion.convertCelsiusToFahrenheit(inputValue)));

                }
                else
                {
                    textView.setText(String.valueOf(DoConversion.convertFahrenheitToCelsius(inputValue)));
                }

                    break;
        }
    }
}
