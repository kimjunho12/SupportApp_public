package com.example.myapplication;

import java.util.ArrayList;

public class bottom_favorite_profile_model {
    private int bottom_favorite_profile_image1;
    private String blank_text;
    private int bottom_favorite_profile_image2;
    private String id;




    public bottom_favorite_profile_model(int bottom_favorite_profile_image1, String blank_text, int bottom_favorite_profile_image2) {
        this.bottom_favorite_profile_image1 = bottom_favorite_profile_image1;
        this.blank_text = blank_text;
        this.bottom_favorite_profile_image2 = bottom_favorite_profile_image2;
    }
    public int getBottom_favorite_profile_image1() {
        return bottom_favorite_profile_image1;
    }

    public void setBottom_favorite_profile_image1(int bottom_favorite_profile_image1) {
        this.bottom_favorite_profile_image1 = bottom_favorite_profile_image1;
    }

    public int getBottom_favorite_profile_image2() {
        return bottom_favorite_profile_image2;
    }

    public void setBottom_favorite_profile_image2(int bottom_favorite_profile_image2) {
        this.bottom_favorite_profile_image2 = bottom_favorite_profile_image2;
    }

    public String getBlank_text() {
        return blank_text;
    }

    public void setBlank_text(String blank_text) {
        this.blank_text = blank_text;
    }

    public static ArrayList<bottom_favorite_profile_model> createContactList(int numContacts) {
        ArrayList<bottom_favorite_profile_model> contacts = new ArrayList<bottom_favorite_profile_model>();
        for(int i=0; i<=numContacts; i++) {
            contacts.add(new bottom_favorite_profile_model(R.mipmap.ic_launcher, "", R.mipmap.ic_launcher));
        }
        return contacts;
    }
    public String getId() {
        return id;
    }
}
