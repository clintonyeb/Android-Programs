package com.example.holys.alarmman;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.holys.alarmman.databinding.DataBindingBinding;

public class MainActivity  extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView();
        DataBindingBinding binding = DataBindingUtil.setContentView(this,R.layout.data_binding);
        User user = new User("Test", "Me Here");
        binding.setUser(user);
        NewMessageNotification.notify(getApplicationContext(), "Message Clinton", 1 );
    }
}
