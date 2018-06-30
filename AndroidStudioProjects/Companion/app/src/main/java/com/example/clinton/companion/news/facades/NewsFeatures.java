package com.example.clinton.companion.news.facades;

import com.example.clinton.companion.utilities.FRAGMENT_ID;

import java.io.Serializable;

public class NewsFeatures implements Serializable {
    static TIME_OUT_CASE time_out_case;
    private static int pageNumber;
    private boolean busySignal;
    private boolean canClear;
    private FRAGMENT_ID FragmentID;
    private String query;

    public TIME_OUT_CASE getTime_out_case() {
        return time_out_case;
    }

    public void setTime_out_case(TIME_OUT_CASE time_out_case) {
        NewsFeatures.time_out_case = time_out_case;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public FRAGMENT_ID getFragmentID() {
        return FragmentID;
    }

    public void setFragmentID(FRAGMENT_ID fragmentID) {
        FragmentID = fragmentID;
    }

    public boolean isCanClear() {
        return canClear;
    }

    public void setCanClear(boolean canClear) {
        this.canClear = canClear;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        NewsFeatures.pageNumber = pageNumber;
    }

    public boolean isBusySignal() {
        return busySignal;
    }

    public void setBusySignal(boolean busySignal) {
        this.busySignal = busySignal;
    }

    public enum TIME_OUT_CASE {
        General
    }

}
