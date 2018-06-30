package com.example.holys.alarmman;

import android.databinding.BaseObservable;

/**
 * Created by holys on 3/10/2016.
 */
public class User extends BaseObservable{
    public final String firstName;
    public final String lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
