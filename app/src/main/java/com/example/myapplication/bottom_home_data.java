package com.example.myapplication;
import android.util.Log;


import java.util.ArrayList;

public class bottom_home_data {

    private int banner;
    private String id;
    private String content;

    public bottom_home_data(int banner) {
        this.id = id;
        this.content = content;
        this.banner = banner;
    }

    public int getBanner() {
        return banner;
    }

    public static ArrayList<com.example.myapplication.bottom_home_data> createContactList(int numContacts) {
        ArrayList<com.example.myapplication.bottom_home_data> contacts = new ArrayList<bottom_home_data>();

        for (int i = 0; i <= numContacts; i++) {
            contacts.add(new bottom_home_data(R.drawable.ic_launcher_background));
        }
        return contacts;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content =content;


    }

}





