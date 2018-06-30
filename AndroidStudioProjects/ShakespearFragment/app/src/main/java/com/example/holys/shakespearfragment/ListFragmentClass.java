package com.example.holys.shakespearfragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListFragmentClass extends ListFragment {

    boolean mDualpane;
    int mCurCheckPosition = 0;

    @Override
    public  void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        String[] titles = getResources().getStringArray(R.array._titles);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, titles));

        View detailsFrame = getActivity().findViewById(R.id.detail_fragmentLayout);
        mDualpane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null)
        {
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualpane)
        {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            showDetails(mCurCheckPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView list, View view, int pos, long id)
    {
        showDetails(pos);
    }

    void showDetails(int index)
    {
        mCurCheckPosition = index;
        if (mDualpane)
        {
            getListView().setItemChecked(index, true);
            DetailFragmentClass details = (DetailFragmentClass)
                    getFragmentManager().findFragmentById(R.id.detail_fragmentLayout);
            if (details == null || details.getShownIndex() != index )
            {
                details = DetailFragmentClass.newInstance(index);

                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                if(index == 0)
                {
                    ft.replace(R.id.detail_fragmentLayout, details);
                }else {
                    ft.replace(R.id.detail_fragmentLayout, details);


                }
                ft.commit();
            }
        }
    }
}
