package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.time.Instant;

import static com.example.myapplication.R.id.parent;
import static com.example.myapplication.R.id.viewpager;

public class bottom_home_viewAdapter extends FragmentPagerAdapter {

    public bottom_home_viewAdapter(FragmentManager fm) {
        super(fm);
    }


    @NonNull
    public Fragment createFragment(int position) {
        if (position == 0)
            return new FragFirst();
        else if (position == 1)
            return new FragSecond();

        return new Fragment();
    }

        public Fragment getItem(int position) {

        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
}






