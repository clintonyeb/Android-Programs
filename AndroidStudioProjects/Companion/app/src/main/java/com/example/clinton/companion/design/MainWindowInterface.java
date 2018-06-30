package com.example.clinton.companion.design;


public interface MainWindowInterface {
    void initializeViews();
    void setEvents();
    void initializeMembers();
    void callToFetchData();
    void makeBusy(boolean state);
    void showProgress();
    void resetProgress();


}
