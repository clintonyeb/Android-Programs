package com.example.clinton.companion.MainFragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.example.clinton.companion.R;
import com.example.clinton.companion.design.MainWindowInterface;
import com.example.clinton.companion.dictionary.adapters.DictionaryAdapter;
import com.example.clinton.companion.dictionary.facades.DictionaryHelper;
import com.example.clinton.companion.home.NewsHomeAdapter;
import com.example.clinton.companion.home.QuoteAdapter;
import com.example.clinton.companion.news.workers.GetUri;
import com.example.clinton.companion.utilities.CursorRecyclerViewAdapter;
import com.example.clinton.companion.utilities.FRAGMENT_ID;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment
        implements MainWindowInterface,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int lifeOrdinal = 0;
    private final int scienceOrdinal = 1;
    private final int sportOrdinal = 2;
    private final int cultureOrdinal = 3;
    private final int worldOrdinal = 4;
    private final int dictionaryOrdinal = 5;
    private final int randomOrdinal = 6;
    private final int quoteOrdinal = 7;
    List<RecyclerView> recyclerViewList;
    List<CursorRecyclerViewAdapter> cursorAdapterList;
    View rootView;
    ViewFlipper viewFlipper;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {}

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void InitializeLoaders()
    {
        getLoaderManager().initLoader(quoteOrdinal, null, this);
        getLoaderManager().initLoader(lifeOrdinal, null, this);
        getLoaderManager().initLoader(scienceOrdinal, null, this);
        getLoaderManager().initLoader(sportOrdinal, null, this);
        getLoaderManager().initLoader(cultureOrdinal, null, this);
        getLoaderManager().initLoader(worldOrdinal, null, this);
        getLoaderManager().initLoader(dictionaryOrdinal, null, this);
        getLoaderManager().initLoader(randomOrdinal, null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews();
        setEvents();
        initializeMembers();
        InitializeAdapters();
        setLayout();
        enableSmoothScroll();
        setAdapters();
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTabsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id)
        {
            case lifeOrdinal:
                return new CursorLoader(getContext(), GetUri.ThisURI(FRAGMENT_ID.LIFESTYLE_ID),
                        null, null, null, "_id LIMIT 1");
            case scienceOrdinal:
                return new CursorLoader(getContext(), GetUri.ThisURI(FRAGMENT_ID.SCIENCE_ID),
                        null, null, null, "_id LIMIT 1");
            case sportOrdinal:
                return new CursorLoader(getContext(), GetUri.ThisURI(FRAGMENT_ID.SPORT_ID),
                        null, null, null, "_id LIMIT 1");
            case cultureOrdinal:
                return new CursorLoader(getContext(), GetUri.ThisURI(FRAGMENT_ID.CULTURE_ID),
                        null, null, null, "_id LIMIT 1");
            case worldOrdinal:
                return new CursorLoader(getContext(), GetUri.ThisURI(FRAGMENT_ID.WORLD_ID),
                        null, null, null, "_id LIMIT 1");
            case dictionaryOrdinal:
                return new CursorLoader(getContext(), DictionaryHelper.GetDictionaryUri(FRAGMENT_ID.MAIN_DICTIONARY_ID),
                        null, null, null, "_id LIMIT 1");
            case randomOrdinal:
                return new CursorLoader(getContext(), DictionaryHelper.GetDictionaryUri(FRAGMENT_ID.RANDOM_WORD_ID),
                        null, null, null, "_id LIMIT 1");
            case quoteOrdinal:
                return new CursorLoader(getContext(), DictionaryHelper.GetDictionaryUri(FRAGMENT_ID.QUOTE_ID),
                        null, null, null, "_id LIMIT 1");
            default:
                throw new IllegalArgumentException();

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        cursorAdapterList.get(id).swapCursor(data);
        if (id < 7)
            showViews(id);
    }

    private void showViews(int id)
    {

      RecyclerView recyclerView = recyclerViewList.get(id);
        if (recyclerView == recyclerViewList.get(quoteOrdinal))
            return;
        if (recyclerView.getAdapter().getItemCount() < 1)
            return;
        if (recyclerView.getParent() == viewFlipper)
            return;

        if (recyclerView.getParent() != null)
        {
            ((ViewGroup)recyclerView.getParent()).removeView(recyclerView);
            viewFlipper.addView(recyclerView);
        }
        else viewFlipper.addView(recyclerView);
    }


    private void showViews()
    {
        for (RecyclerView recyclerView :
                recyclerViewList) {
            if (recyclerView.getAdapter().getItemCount() < 1)
                continue;
            if (recyclerView.getParent() == viewFlipper)
                continue;
            if (recyclerView == recyclerViewList.get(quoteOrdinal))
                continue;
            if (recyclerView.getParent() != null)
            {
                ((ViewGroup)recyclerView.getParent()).removeView(recyclerView);
                viewFlipper.addView(recyclerView);
            }
            else viewFlipper.addView(recyclerView);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        int id = loader.getId();
        cursorAdapterList.get(id).swapCursor(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitializeLoaders();

    }

    private void InitializeAdapters()
    {
        cursorAdapterList = new ArrayList<>(8);

        NewsHomeAdapter lifeAdapter = new NewsHomeAdapter(getContext(), null, FRAGMENT_ID.NEWS_HOME_LIFE);
        NewsHomeAdapter sportAdapter= new NewsHomeAdapter(getContext(), null, FRAGMENT_ID.NEWS_HOME_SPORT);
        NewsHomeAdapter scienceAdapter = new NewsHomeAdapter(getContext(), null, FRAGMENT_ID.NEWS_HOME_SCIENCE);
        NewsHomeAdapter cultureAdapter = new NewsHomeAdapter(getContext(), null, FRAGMENT_ID.NEWS_HOME_CULTURE);
        NewsHomeAdapter worldAdapter = new NewsHomeAdapter(getContext(), null, FRAGMENT_ID.NEWS_HOME_WORLD);
        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(getContext(), null, FRAGMENT_ID.DICTIONARY_HOME);
        DictionaryAdapter randomAdapter = new DictionaryAdapter(getContext(), null, FRAGMENT_ID.RANDOM_HOME);
        QuoteAdapter quoteAdapter = new QuoteAdapter(getContext(), null);

        cursorAdapterList.add(lifeAdapter);
        cursorAdapterList.add(sportAdapter);
        cursorAdapterList.add(scienceAdapter);
        cursorAdapterList.add(cultureAdapter);
        cursorAdapterList.add(worldAdapter);
        cursorAdapterList.add(dictionaryAdapter);
        cursorAdapterList.add(randomAdapter);
        cursorAdapterList.add(quoteAdapter);
    }

    private void setLayout()
    {
        for (RecyclerView recyclerView :
                recyclerViewList)
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setAdapters()
    {
        for (int i = 0; i < recyclerViewList.size(); i ++)
            recyclerViewList.get(i).setAdapter(cursorAdapterList.get(i));

    }

    @Override
    public void initializeViews() {
        recyclerViewList = new ArrayList<>(8);
        RecyclerView lifeRecyclerView = (RecyclerView) rootView.findViewById(R.id.news_life);
        RecyclerView sportRecyclerView = (RecyclerView) rootView.findViewById(R.id.news_sport);
        RecyclerView scienceRecyclerView = (RecyclerView) rootView.findViewById(R.id.news_science);
        RecyclerView cultureRecyclerView = (RecyclerView) rootView.findViewById(R.id.news_culture);
        RecyclerView worldRecyclerView = (RecyclerView) rootView.findViewById(R.id.news_world);
        RecyclerView dictionaryRecyclerView = (RecyclerView) rootView.findViewById(R.id.dictionary_view);
        RecyclerView randomRecyclerView = (RecyclerView) rootView.findViewById(R.id.random_word_view);
        RecyclerView quoteRecyclerView = (RecyclerView) rootView.findViewById(R.id.quoteRecyclerView);

        recyclerViewList.add(lifeRecyclerView);
        recyclerViewList.add(sportRecyclerView);
        recyclerViewList.add(scienceRecyclerView);
        recyclerViewList.add(cultureRecyclerView);
        recyclerViewList.add(worldRecyclerView);
        recyclerViewList.add(dictionaryRecyclerView);
        recyclerViewList.add(randomRecyclerView);
        recyclerViewList.add(quoteRecyclerView);

        viewFlipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);

        setHasFixedSize();
    }
    private void setHasFixedSize()
    {
        for (RecyclerView recyclerView:
             recyclerViewList)
            recyclerView.setHasFixedSize(true);
    }

    private void enableSmoothScroll()
    {
        for (RecyclerView recyclerView :
                recyclerViewList)
            recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void initializeMembers() {

    }

    @Override
    public void onStart() {
        super.onStart();
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_out));
        viewFlipper.removeAllViews();
        showViews();
        if (!viewFlipper.isFlipping())
            viewFlipper.startFlipping();
    }



    @Override
    public void callToFetchData() {

    }

    @Override
    public void makeBusy(boolean state) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void resetProgress() {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
