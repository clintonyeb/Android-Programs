package com.example.appvilleegg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.applicasa.ApplicasaManager.LiStore;
import com.applicasa.VirtualCurrency.VirtualCurrency;
import com.appvilleegg.R;
import com.example.appvilleegg.adapters.VirtualCurrencyAdapter;
import com.example.appvilleegg.sampleApp.TabsFragmentActivity;

import applicasa.LiCore.LiLogger;


public class VirtualCurrencyFragment extends Fragment implements GridView.OnItemClickListener{
	/** (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	String Tag = VirtualCurrencyFragment.class.getSimpleName();
	
    private GridView                mGridView;
    private VirtualCurrencyAdapter  			mStoreAdapter = null;
	   
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View v = inflater.inflate(R.layout.grid_frag, container, false);
		mGridView = (GridView) v.findViewById(R.id.grid_view);
		mGridView.setVisibility(View.VISIBLE);	
	    return v;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		 if (getActivity() != null ) {
	            // Create an instance of the custom adapter for the GridView. A static array of location data
	            // is stored in the Application sub-class for this app. This data would normally come
	            // from a database or a web service.
			 if (mStoreAdapter == null)
			 	mStoreAdapter = VirtualCurrencyAdapter.getInstance(getActivity(), LiStore.getAllVirtualCurrency());
	            
	            if (mGridView != null) {
	                mGridView.setAdapter(mStoreAdapter);
	            }
	            mGridView.setOnItemClickListener(this);
		 }
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		LiLogger.logWarning(Tag, "Clicked");
		if (TabsFragmentActivity.clickEnabled)	
		{
			VirtualCurrency vc = LiStore.getAllVirtualCurrency().get(position);
			LiStore.buyVirtualCurrency(getActivity(), vc, TabsFragmentActivity.purchaseCallback);
		}
	}
	
	
	@Override
	public void onPause(){
		super.onPause();
		LiLogger.logInfo("TABS Virtual" , "on pause");
	}


	@Override
	public void onResume() {
		super.onResume();
		LiLogger.logInfo("TABS Virtual" , "on resume");
	}
}
