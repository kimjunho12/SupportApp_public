package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class bottom_home_viewpager_adapter extends FragmentStateAdapter {
    private ArrayList<Fragment> items = new ArrayList<Fragment>();
    public int mCount;

    public bottom_home_viewpager_adapter(@NonNull bottom_home_fragment fm, int count) {
        super(fm);
        mCount = count;
    }

    public void addItem(Fragment item) {
        items.add(item);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new viewpager_FirstFragment();
        else if(index==1) return new viewpager_SecondFragment();
        else return new viewpager_ThirdFragment();
    }

    public int getItemCount() {
        return 2000;
    }
    public int getRealPosition (int position){
        return position % mCount;
    }

}