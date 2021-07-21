package com.example.myapplication.models;

public class User {
    private String Uid;
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String birth;

    private String photoURL;
    private int is_target;  // 0 : 일반, 1 : 후원 대상
    private Boolean is_surveyed;


    public User() {
    }

    public User(String uid, String id, String pw, String name, String phone, String birth, int is_target) {
        this.Uid = uid;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.birth = birth;

        this.photoURL = "null";
        this.is_target = is_target;
    }

    public User(String uid, String id, String pw, String name, String phone, String birth, String photoURL, int is_target) {
        this.Uid = uid;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.photoURL = photoURL;
        this.is_target = is_target;
    }

    public void setIs_surveyed(Boolean is_surveyed) {
        this.is_surveyed = is_surveyed;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
    {

        this.is_target =is_target;


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


    public String getPhotoURL() {
        return photoURL;
    }

    public Boolean getIs_surveyed() {
        return is_surveyed;
    }

    public void setIs_target(int is_target) {
        this.is_target = is_target;
    }

}
