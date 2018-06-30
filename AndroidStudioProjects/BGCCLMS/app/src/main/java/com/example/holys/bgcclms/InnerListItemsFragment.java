package com.example.holys.bgcclms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class InnerListItemsFragment extends Fragment {

    String[] mItems;
    ArrayAdapter mAdapter;
    ListView mListView;
    public InnerListItemsFragment () {
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        boolean bool = getResources().getBoolean(R.bool.landscape);

        if(bool)
        {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        View view = inflater.inflate(R.layout.fragment_inner_list_items, container, false);
        Intent intent = getActivity().getIntent();
        String toDo = intent.getStringExtra(Intent.EXTRA_TEXT);

        switch (toDo)
        {
            case "Announcements":
                mItems = new String[]
                        {

                        };
                break;
            case "Articles":
                mItems = new String[]
                        {

                        };
                break;

            case "Profile":
                mItems = new String[]
                        {
                                "Attendance",
                                "MST Scores",
                                "Assessment",
                                "Change Password"

                        };
                break;

            case "School Events":
                mItems = new String[]
                        {

                        };
                break;

            case "World News":
                mItems = new String[]
                        {
                                "Science & Technology",
                                "Health",
                                "Business",
                                "Sports",
                                "Arts & Entertainment",
                                "Culture & Politics",
                                "Gaming",
                                "Law and Crime",
                                "Computers and Internet",
                                "Weather",
                                "Recreation",
                                "Religion"

                        };
                break;
            case "Study Online":
                mItems = new String[]
                        {

                        };
                break;

            case "Grievances":
                mItems = new String[]
                        {

                        };
                break;

        }
        mAdapter = new ArrayAdapter(getActivity(), R.layout.detail_list_item, R.id.detail_list_item_textView,  mItems);
        mListView = (ListView)view.findViewById(R.id.detailListView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                mListView.setItemChecked(position, true);
                Intent intent = new Intent(getActivity(), NewsDataDisplay.class);
                intent.putExtra(Intent.EXTRA_TEXT, position);
                startActivity(intent);
            }
        });
        getActivity().setTitle(toDo);
        return view;
    }


}
