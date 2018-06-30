package com.example.clinton.light.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatting {

    public static String ParseDate (String dateR) {
        SimpleDateFormat full_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

        try {
            full_date.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = full_date.parse(dateR);
            if(isDateInCurrentWeek(date))
            {

                return GetWeekDate(date);
            }
            else
            {
                return GetDatePast(date);
            }

        } catch (Exception e) {
            System.err.println("Error" + e);
        }
        return dateR;
    }
    public static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }
    private static String GetDatePast(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return day + "th " + GetMonth(month) + ", " + year;

    }

    private static String GetMonth(int m)
    {
        switch(m)
        {
            case Calendar.JANUARY:
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "December";
            default:
                return "";

        }
    }
    private static String GetWeekDate(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String day = GetDay(c.get(Calendar.DAY_OF_WEEK));
        int hr = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        return day + ", " +  GoodDay(hr)+ ":"  + GoodDay(min);
    }

    private static String GoodDay(int day)
    {
        String d = String.valueOf(day);
       if (day < 10)
       {
           d = "0" + d;
       }
        return d;
    }

   /* private static String CorrectRepresentDay(int day)
    {
        String tosend;
        if (day == 1)
        {
            tosend = "st";
        }
        else if (day == 2)
        {
            tosend = "nd";
        }
        else if (day == 3)
        {
            tosend = "rd";
        }
        else
        {
            tosend = "th";
        }
        return tosend;
    }*/
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
