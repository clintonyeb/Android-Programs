package com.example.clinton.companion.todayword;

public class Definitions {

    String text = "";
    String partOfSpeech = "";

    public static Definitions newInstance()
    {
        return new Definitions();
    }


    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
