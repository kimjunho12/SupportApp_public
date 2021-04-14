package com.example.myapplication.models;

import java.util.List;

public class Subject {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public int type;
    public String text;
    public List<Subject> invisibleChildren;

    public Subject() {
    }

    public Subject(int type, String text) {
        this.type = type;
        this.text = text;
    }
}
