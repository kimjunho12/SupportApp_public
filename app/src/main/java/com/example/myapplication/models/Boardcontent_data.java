package com.example.myapplication.models;

public class Boardcontent_data {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Boardcontent_data(){}

    public Boardcontent_data(String name, String reply) {
        this.name = name;
        this.reply = reply;
    }

    private String name;
    private String reply;

}


