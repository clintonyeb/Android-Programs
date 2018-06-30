package com.example.clinton.test1;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String dateR =  "2016-03-21T09:53:27Z";
        System.out.print(ParseDate(dateR));
    }

    public static String ParseDate (String dateR) {
        SimpleDateFormat full_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            full_date.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = full_date.parse(dateR);
            Date date1 = d.parse(d.format(date));
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            String t = time.format(date);
            String day = GetDay(c.get(Calendar.DAY_OF_WEEK));
            return day + ", " + t;
        } catch (Exception e) {
            System.err.println("Error" + e);
        }
        return dateR;
    }

    private static String GetDay(int d)
    {
        switch (d)
        {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case  7:
                return "Saturday";
            default:
                return "";

        }
    }
}
