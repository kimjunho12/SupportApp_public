package com.example.myapplication;


import java.util.ArrayList;

public class bottom_home_data {

    private int banner;
    private int banner1;
    private String id;
    private String content;

    public bottom_home_data(int banner1, int banner) {
        this.id = id;
        this.content = content;
        this.banner = banner;
        this.banner1 = banner1;
    }

    public int getBanner() {
        return banner;
    }

    public int getBanner1() {

        return banner1;
    }

    public static ArrayList<bottom_home_data> createContactList(int numContacts) {
        ArrayList<bottom_home_data> contacts = new ArrayList<bottom_home_data>();

        for (int i = 0; i <= numContacts; i++) {
            contacts.add(new bottom_home_data(R.mipmap.ic_launcher,  R.mipmap.ic_launcher));
        }
        return contacts;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }
    public void setBanner1(int banner1) {
        this.banner1 = banner1;
    }


    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content =content;
    }

}





