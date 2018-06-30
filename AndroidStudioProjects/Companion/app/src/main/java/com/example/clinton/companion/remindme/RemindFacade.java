package com.example.clinton.companion.remindme;

import java.util.Date;

public class RemindFacade {
    private Date date;
    private boolean isSet;
    private RemindPriority remindPriority;
    private String toDo;
    private RemindType remindType;
    private String ringTone;


    public RemindType getRemindType() {
        return remindType;
    }

    public void setRemindType(RemindType remindType) {
        this.remindType = remindType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public RemindPriority getRemindPriority() {
        return remindPriority;
    }

    public void setRemindPriority(RemindPriority remindPriority) {
        this.remindPriority = remindPriority;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }




}
