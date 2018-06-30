package com.example.clinton.companion.tabsFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.clinton.companion.R;
import com.example.clinton.companion.design.MainWindowInterface;
import com.example.clinton.companion.home.NewsHomeAdapter;
import com.example.clinton.companion.news.workers.GetUri;
import com.example.clinton.companion.news.workers.HandleNewsData;
import com.example.clinton.companion.news.adapters.NewsAdapter;
import com.example.clinton.companion.news.facades.NewsFeatures;
import com.example.clinton.companion.news.facades.NewsResults;
import com.example.clinton.companion.utilities.EndlessRecyclerViewScrollListener;
import com.example.clinton.companion.utilities.HelperMethods;
import com.example.clinton.companion.utilities.QuickstartPreferences;

public class AllTabsSuper extends Fragment implements
        MainWindowInterface,
        LoaderManager.LoaderCallbacks<Cursor>{

    private static int PAGE_NUMBER = 1;
    protected NewsFeatures newsFeatures;
    View rootView;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsAdapter adapter;
    NewsHomeAdapter newsHomeAdapter;
    LinearLayoutManager linear;
    boolean isFinished = true;
    int dataCount = 0;
    private OnTabsFragmentInteractionListener mListener;
    private boolean allowToSwipe = true;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ProgressBar progressBar;

    public AllTabsSuper() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(newsFeatures.getFragmentID().ordinal(), null, this);
        switch (newsFeatures.getFragmentID())
        {
            case NEWS_FAVORITE_FRAG_ID:
                newsHomeAdapter = new NewsHomeAdapter(this.getContext(),null, newsFeatures.getFragmentID());
                recyclerView.setAdapter(newsHomeAdapter);
                break;
            default:
                adapter = new NewsAdapter(this.getContext(), null, newsFeatures.getFragmentID());
                recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.SERVICE_COMPLETE));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_tabs, container, false);
        initializeViews();
        setEvents();
        initializeMembers();
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTabsFragmentInteractionListener) {
            mListener = (OnTabsFragmentInteractionListener) context;
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
    public void initializeViews() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.lRecyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.lSwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorPrimary);
        recyclerView.setHasFixedSize(true);
        linear  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linear);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
    }

    @Override
    public void setEvents() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (allowToSwipe)
                {
                    swipeRefreshLayout.setRefreshing(false);
                    newsFeatures.setCanClear(true);
                    PAGE_NUMBER = 1;
                    allowToSwipe = false;
                    callToFetchData();
                }else resetProgress();

            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linear) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (!newsFeatures.isBusySignal() && totalItemsCount < 30 && isFinished)
                {
                    PAGE_NUMBER ++;
                    newsFeatures.setCanClear(false);
                    callToFetchData();
                }
            }
        });
    }

    @Override
    public void initializeMembers() {
        newsFeatures = new NewsFeatures();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                NewsResults newsResults = (NewsResults) intent.getSerializableExtra(HandleNewsData.SERVICE_RESULT);
                String text = null;
                Snackbar snackbar = null;
                switch (newsResults)
                {
                    case Timeout:
                        text =  "Network too slow";
                        snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                        snackbar.setAction("Settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                            }
                        });
                        break;
                    case Network:
                        text =  "Network too slow";
                        snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                        snackbar.setAction("Settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                            }
                        });
                        break;
                    case NoConnection:
                        text = "Not connected";
                        snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                        snackbar.setAction("Settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                            }
                        });
                        break;
                    case Success:
                        break;
                    case AddedToFavorites:
                        text = "Added to Favorites";
                        snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                        snackbar.setAction("Check", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mListener.onItemClicked(1);
                            }
                        });
                        break;
                    case RemovedFromFavorites:
                        text = "Removed from Favorites";
                        snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
                        snackbar.setAction("Check", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mListener.onItemClicked(1);
                            }
                        });
                        break;
                    default:
                        text = "Something went wrong";
                        break;

                }
                if (snackbar != null) {
                    snackbar.show();
                }
                resetProgress();
            }
        };
    }

    @Override
    public void callToFetchData() {
        if (newsFeatures == null)return;
        if (!newsFeatures.isBusySignal() && isFinished)
        {
            if (HelperMethods.CheckConnectionState(getContext()))
            {
                newsFeatures.setPageNumber(PAGE_NUMBER);
                HandleNewsData handleNewsData = new HandleNewsData();
                handleNewsData.handleFetchingNews(getContext(), newsFeatures);
                showProgress();
            }
            else
            {
                Snackbar.make(rootView, "Network Unavailable", Snackbar.LENGTH_LONG)
                .setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    }
                })
                .show();
                resetProgress();
            }

        }
    }

    @Override
    public void makeBusy(boolean state) {
        newsFeatures.setBusySignal(state);
    }

    @Override
    public void showProgress() {
        makeBusy(true);
        allowToSwipe = false;
        isFinished = false;
    }


    @Override
    public void resetProgress() {
        swipeRefreshLayout.setRefreshing(false);
        allowToSwipe = true;
        isFinished = true;
        makeBusy(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), GetUri.ThisURI(newsFeatures.getFragmentID()), null,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (newsFeatures.getFragmentID())
        {
            case NEWS_FAVORITE_FRAG_ID:
                if (loader == null)
                    return;
                newsHomeAdapter.swapCursor(data);
                break;
            default:
                if (loader == null)
                    return;
                adapter.swapCursor(data);
                dataCount = adapter.getItemCount();
                if (dataCount < 1)
                {
                    isFinished = true;
                    callToFetchData();
                    progressBar.setVisibility(View.VISIBLE);
                }
                else progressBar.setVisibility(View.GONE);

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (newsFeatures.getFragmentID())
        {
            case NEWS_FAVORITE_FRAG_ID:
                newsHomeAdapter.swapCursor(null);
                break;
            default:
                adapter.swapCursor(null);
                break;
        }

    }

    public interface OnTabsFragmentInteractionListener {
        void onFragmentInteraction(boolean state);
        void onItemClicked(int id);
    }
}
