package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class bottom_home_fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private bottom_home_adapter bottom_home_adapter;
    private ArrayList<bottom_home_data> list = new ArrayList<>();

    private ViewPager viewPager;
    private viewpager_FirstFragment fragment1;
    private viewpager_SecondFragment fragment2;
    private viewpager_ThirdFragment fragment3;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_home_fragment, container, false);

        //recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);// 리사이클러뷰 id
        list = bottom_home_data.createContactList(5);
        // recyclerView.setHasFixedSize(true);
        bottom_home_adapter = new bottom_home_adapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bottom_home_adapter);

        //viewpager
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        bottom_home_viewpager_adapter adapter = new bottom_home_viewpager_adapter(getChildFragmentManager());
        fragment1 = new viewpager_FirstFragment();
        adapter.addItem(fragment1);
        fragment2 = new viewpager_SecondFragment();
        adapter.addItem(fragment2);
        fragment3 = new viewpager_ThirdFragment();
        adapter.addItem(fragment3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        return view;
    }
}




