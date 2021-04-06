package com.example.myapplication;
import android.util.Log;

import java.util.ArrayList;


public class bottom_event_news_model {
    private int bottom_event_news_model_image;
    private String bottom_event_news_model_content;

    public bottom_event_news_model(int bottom_event_news_model_image) {
        this.bottom_event_news_model_image = bottom_event_news_model_image;
        //this.bottom_event_news_model_content = bottom_event_news_model_content;
    }

    public int getBottom_event_news_model_image() {
        return bottom_event_news_model_image;
    }

    public static ArrayList<bottom_event_news_model> createContactList(int numContacts) {
        ArrayList<bottom_event_news_model> contacts = new ArrayList<bottom_event_news_model>();
        for(int i=0; i<=numContacts; i++) {
            contacts.add(new bottom_event_news_model(R.mipmap.ic_banner));
        }
        return contacts;
    }

    public void setBottom_event_news_model_image(int bottom_event_news_model_image) {
        this.bottom_event_news_model_image = bottom_event_news_model_image;
    }

    public String getBottom_event_news_model_content() {
        return bottom_event_news_model_content;
    }

    public void setBottom_event_news_model_content(String bottom_event_news_model_content) {
        this.bottom_event_news_model_content = bottom_event_news_model_content;
    }
}
