package com.example.clinton.companion.design;


import com.example.clinton.companion.MainFragments.DictionaryFragment;
import com.example.clinton.companion.MainFragments.NewsFragment;
import com.example.clinton.companion.MainFragments.NewsSearchFragment;
import com.example.clinton.companion.tabsFragments.AllTabsSuper;
import com.example.clinton.companion.tabsFragments.SearchFrag;

public interface MainActivityInterface extends MainWindowInterface,
        NewsFragment.OnNewsFragmentInteractionListener,
        AllTabsSuper.OnTabsFragmentInteractionListener,
        DictionaryFragment.OnMainDictionaryListener,
        NewsSearchFragment.OnFragmentInteractionListener,
        SearchFrag.OnSearchFragmentInteractionListener{

}


