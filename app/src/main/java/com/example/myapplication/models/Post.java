package com.example.myapplication.models;

import java.util.Date;

public class Post {

    //    public int key;
    public int type;
    public String title;
    // userkey로 matching
    public String username;
    public Date date;
    public int view_cnt;

    public Post(int type, String title, String username) {
        this.type = type;
        this.title = title;
        this.username = username;
    }

    public Post(int type, String title, String username, Date date, int view_cnt) {
        this.type = type;
        this.title = title;
        this.username = username;
        this.date = date;
        this.view_cnt = view_cnt;
    }
}
