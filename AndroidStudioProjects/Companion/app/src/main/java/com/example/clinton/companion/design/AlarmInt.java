package com.example.clinton.companion.design;


import com.example.clinton.companion.remindme.RemindFacade;

public interface AlarmInt {
    RemindFacade getNextAlarm();
    void setAlarm(RemindFacade remindFacade);
}
