package com.example.myapplication.models;

public class User {
    private String Uid;
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String birth;
    private int is_target;  // 0 : 일반, 1 : 후원 대상
    private boolean is_surveyed;

    public User() {

    }

    public User(String uid, String id, String pw, String name, String phone, String birth, int is_target){
        this.Uid = uid;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.is_target = is_target;
    }

    public void setIs_surveyed(boolean is_surveyed) {
        this.is_surveyed = is_surveyed;
    }

    public String getUid() {
        return Uid;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirth() {
        return birth;
    }

    public int getIs_target() {
        return is_target;
    }
}
