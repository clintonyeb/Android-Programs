package com.example.holys.light;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@l} interface
 * to handle interaction events.
 * Use the {@link TodayNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayNewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewPager viewPager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public TodayNewsFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayNewsFragment newInstance (String param1, String param2) {
        TodayNewsFragment fragment = new TodayNewsFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_today_news, container, false);
        // Inflate the layout for this fragment
        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);
        TabsAdapter tabsAdapter = new TabsAdapter(getChildFragmentManager());
        viewPager.setAdapter(tabsAdapter);


        final TabLayout.Tab News1 = tabLayout.newTab();
        final TabLayout.Tab News2 = tabLayout.newTab();
        final TabLayout.Tab News3 = tabLayout.newTab();

        News1.setText("News Today");
        News2.setText("News2");
        News3.setText("News3");

        //News1.setIcon(R.drawable.news);
        tabLayout.addTab(News1, 0);
        tabLayout.addTab(News2, 1);
        tabLayout.addTab(News3, 2);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getActivity(), R.drawable.tab_selected));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.icons));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected (TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected (TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected (TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:

                        //setting Home as White and rest grey
                        //and like wise for all other positions

                        /*home.setIcon(R.drawable.ic_home_white);
                        inbox.setIcon(R.drawable.ic_inbox_grey);
                        star.setIcon(R.drawable.ic_star_grey);*/
                        break;
                    case 1:
                       /* home.setIcon(R.drawable.ic_home_grey);
                        inbox.setIcon(R.drawable.ic_inbox_white);
                        star.setIcon(R.drawable.ic_star_grey);*/
                        break;
                    case 2:
                        /*home.setIcon(R.drawable.ic_home_grey);
                        inbox.setIcon(R.drawable.ic_inbox_grey);
                        star.setIcon(R.drawable.ic_star_white);*/
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return rootView;
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
        /*if (context instanceof OnFragmentInteractionListener) {
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }*/
}
