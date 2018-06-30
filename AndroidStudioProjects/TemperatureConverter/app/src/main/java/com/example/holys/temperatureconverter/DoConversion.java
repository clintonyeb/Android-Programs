package com.example.holys.temperatureconverter;

/**
 * Created by holys on 2/1/2016.
 */
public class DoConversion {
    public  static float convertFahrenheitToCelsius(float fah)
    {
        return ((fah - 32) * 5 / 9);
    }

    public  static  float convertCelsiusToFahrenheit(float cel)
    {
        return ((cel * 9) / 5) + 32;
    }
}
