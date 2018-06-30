package com.example.clinton.light.menuFragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clinton.light.R;
import com.example.clinton.light.activities.MainActivity;
import com.example.clinton.light.database.NewsContract;
import com.example.clinton.light.fetchData.FetchNewsIntentService;
import com.example.clinton.light.tabs.TabsSuperClass;
import com.example.clinton.light.utilities.DialogFacade;
import com.example.clinton.light.utilities.DialogFrag;

public class AskMeFragment extends TabsSuperClass
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int POSITION = 6;
    DialogFacade dialogFacade;
    AskMeReadyToLoadData mListener;

    @Override public View onCreateView (LayoutInflater inflater, ViewGroup container,
                                        Bundle savedInstanceState)
    {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                showNoticeDialog();
                break;
        }
        return true;
    }
    public void showNoticeDialog() {
        DialogFragment dialog = new DialogFrag();
        dialog.show(getActivity().getFragmentManager(), "DialogFrag");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(POSITION, null, this);
    }
    private void ResetValues()
    {
        if (_shdClear) {
            pageNumber = 1;
            CLEAR = true;
            _shdClear = false;
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void CallFetchData() {
    }

    public void CallFetchData(DialogFacade facade)
    {
        dialogFacade =facade;
        if (CheckConnectionState() && !MainActivity.SEARCH_STATE) {
                ResetValues();
                Log.i("CALL FETCH", "called");
                MainActivity.SEARCH_STATE = true;
                _isLoading = true;
                Intent intent = new Intent(getContext(), FetchNewsIntentService.class);
                intent.putExtra(FetchNewsIntentService.SERVICE_DATA, POSITION);
                intent.putExtra(FetchNewsIntentService.SERVICE_STATE, CLEAR);
                intent.putExtra(FetchNewsIntentService.SEARCH_DATA, dialogFacade.getSearchWord());
                intent.putExtra(FetchNewsIntentService.POSITION_DATA, dialogFacade.getPosition());
                //this.recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                this.swipeRefreshLayout.setRefreshing(true);
                getContext().startService(intent);
        }
        else
        {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "You are offline", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), NewsContract.CONTENT_URI_SEARCH, null, null, null, null );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.getCount() == 0 )
        {
            DialogFacade facade = new DialogFacade();
            facade.setPosition(2);
            facade.setSearchWord("apple+company");
            CallFetchData(facade);
        }
        else
        {
            MainActivity.SEARCH_STATE = false;
                this.progressBar.setVisibility(View.GONE);
                this.mButton.setVisibility(View.GONE);
                this.swipeRefreshLayout.setRefreshing(false);
             _isLoading = false;
            mAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public interface AskMeReadyToLoadData
    {
        void BeginLoadData();
    }

    @Override
    public void onStart() {
        super.onStart();
        mListener.BeginLoadData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            mListener = (AskMeReadyToLoadData) activity;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()+
                    "must implement interface");
        }
    }
}
