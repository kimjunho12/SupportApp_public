package com.example.myapplication.models;

import java.util.List;

public class Subject {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public int type;
    public String lCategory;
    public String mCategory;
    public String sCategory;
    public List<Subject> invisibleChildren;

    public Subject() {
    }

    public Subject(int type, String Category) {
        this.type = type;
        if (type == HEADER) {
            this.lCategory = Category;
        } else {
            this.mCategory = Category;
        }
    }
}
