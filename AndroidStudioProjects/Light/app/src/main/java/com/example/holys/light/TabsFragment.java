package com.example.holys.light;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.holys.light.NEWS_DIR.EndlessRecyclerViewScrollListener;
import com.example.holys.light.NEWS_DIR.FetchNewsDataJSON;
import com.example.holys.light.NEWS_DIR.NewsCustomCardAdapter;
import com.example.holys.light.NEWS_DIR.NewsFacade;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TabsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    public List<NewsFacade> mNewsData;
    public NewsCustomCardAdapter mAdapter;
    public RecyclerView recyclerView;
    FetchNewsDataJSON mAsyncTodo;
    public static int pageSize = 1;
    public boolean _isLoading = false;
    LinearLayoutManager mLinear;
    public ProgressBar progressBar;
    public Button mButton;

    public TabsFragment () {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TabsFragment newInstance (String param1, String param2) {
        TabsFragment fragment = new TabsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(true);
         mLinear  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinear);
        mButton = (Button)rootView.findViewById(R.id.loadButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                mButton.setVisibility(View.GONE);
                CallFetchData();
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLinear) {
            @Override
            public void onLoadMore (int page, int totalItemsCount) {
            if(mButton.getVisibility() == View.GONE &&
                    progressBar.getVisibility() == View.GONE &&
                    !_isLoading &&
                    mNewsData.size() < 30)
            {
                mButton.setVisibility(View.VISIBLE);
            }

            }
        });
        mNewsData = new ArrayList<>();
        mAdapter = new NewsCustomCardAdapter(mNewsData, TabsFragment.this);
        recyclerView.setAdapter(mAdapter);
        progressBar  = (ProgressBar) rootView.findViewById(R.id.progressBar);
        CallFetchData();
        return rootView;
    }



    public void CallFetchData()
    {
        if (CheckConnectionState() )
        {
            mAsyncTodo = new FetchNewsDataJSON();
            if(!_isLoading && mNewsData.size() < 30)
            {
                progressBar.setVisibility(View.VISIBLE);
                mAsyncTodo.execute(TabsFragment.this);
                _isLoading = true;
            }
        }
        else
            Toast.makeText(getContext(), "You are offline", Toast.LENGTH_LONG).show();
    }


    private void WaitSomeTime()
    {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.v("TIMER", "seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.v("TIMER", "Finished");
            }
        }.start();
    }

    private  boolean CheckConnectionState()
    {
        ConnectivityManager manager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info .isConnected())
        {
            return true;
        }
        else
            return false;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed (Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach () {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }*/
}
