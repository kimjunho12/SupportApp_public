package com.example.myapplication.models;

import android.graphics.drawable.Icon;

public class Target {
    public String name;
    public Icon icon;

    public Target(String name){
        this.name = name;
    }

    public Target(String name, Icon image){
        this.name = name;
        this.icon = image;
    }

}
