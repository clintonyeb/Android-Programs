package com.getbase.floatingactionbutton.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class MainActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final View actionB = findViewById(R.id.action_b);

    /*FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
    actionC.setTitle("Hide/Show Action above");
    actionC.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
      }
    });

    final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
    menuMultipleActions.addButton(actionC);

    ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
    drawable.getPaint().setColor(getResources().getColor(R.color.white));*/


    final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
    actionA.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        actionA.setTitle("Action A clicked");
      }
    });

    // Test that FAMs containing FABs with visibility GONE do not cause crashes


    FloatingActionButton addedOnce = new FloatingActionButton(this);
    addedOnce.setTitle("Added once");
    FloatingActionButton addedTwice = new FloatingActionButton(this);
    addedTwice.setTitle("Added twice");

  }
}
