package com.example.clinton.companion.dictionaryFragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.clinton.companion.R;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

public class RandomWord extends AllDictionaryFragment {
    MenuItem menuItem;

    public RandomWord() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Fragment_ID = FRAGMENT_ID.RANDOM_WORD_ID;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_dictionary, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
        menuItem = menu.findItem(R.id.action_refreshing);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                callToFetchData();
                return true;
            }
        });
    }

    public void ResetUpdating()
    {
        if(menuItem != null)
        {
            if(menuItem.getActionView() != null)
            {
                menuItem.getActionView().clearAnimation();
                menuItem.setActionView(null);
            }
        }
    }

    protected void RefreshState()
    {
        if(menuItem != null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView iv = (ImageView)inflater.inflate(R.layout.progress_ring, null);
            Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_refresh);
            rotate.setRepeatCount(Animation.INFINITE);
            iv.startAnimation(rotate);
            menuItem.setActionView(iv);
        }
    }
    @Override
    public void showProgress() {
        RefreshState();
        super.showProgress();
    }

    @Override
    public void resetProgress() {
        ResetUpdating();
        super.resetProgress();
    }


    @Override
    public void onPause() {
        resetProgress();
        super.onPause();
    }
}
