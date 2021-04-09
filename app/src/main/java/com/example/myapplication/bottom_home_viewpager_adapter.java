package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class bottom_home_viewpager_adapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items = new ArrayList<Fragment>();

    public bottom_home_viewpager_adapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public void addItem(Fragment item) {
        items.add(item);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }
}
