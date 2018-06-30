package com.example.holys.bgcclms;

//import android.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ListView listView;
    private onItemSelectedInterface listener;
    String[] list;
    //public static int mPosition;
    public final static String SELECTED_KEY = "itemPosition";
    public MainActivityFragment () {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.frag_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), Settings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public void onViewStateRestored (Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if(mPosition != listView.INVALID_POSITION)
//        {
//         listView.smoothScrollToPosition(mPosition);
//        }
//    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // boolean bool = getResources().getBoolean(R.bool.landscape);
//        if(mPosition != listView.INVALID_POSITION)
//        {
//         listView.smoothScrollToPosition(mPosition);
//        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        list = new String[]{
                "Announcements",
                "Articles",
                "Profile",
                "School Events",
                "News Today",
                "Study Online",
                "Grievances",
        };

        int[] imageId = new int[]{
                R.drawable.announce,
                R.drawable.article,
                R.drawable.profile,
                R.drawable.school,
                R.drawable.news,
                R.drawable.study,
                R.drawable.grieve
        };


        List<String> arrayList = new ArrayList<String>(Arrays.asList(list));
        listView = (ListView)view.findViewById(R.id.myListVeiw);
        listView.setAdapter(new CustomAdapter(this, (MainActivity) getActivity(), list, imageId));
//        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY))
//        {
//            mPosition = savedInstanceState.getInt(SELECTED_KEY);
//        }
        return  view;
    }

    public interface onItemSelectedInterface
    {
        public void onListItemSelected(String message);
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        if (context instanceof onItemSelectedInterface)
        {
            listener = (onItemSelectedInterface)context;
        }
        else
        {
            throw new ClassCastException(context.toString()
                    + " must implemenet MyListFragment.OnItemSelectedInterface");
        }
    }

//    @Override
//    public void onSaveInstanceState (Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if(mPosition != listView.INVALID_POSITION)
//        {
//            outState.putInt(SELECTED_KEY, mPosition );
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void updateDetail(String uri) {
        listener.onListItemSelected(uri);
    }
}
