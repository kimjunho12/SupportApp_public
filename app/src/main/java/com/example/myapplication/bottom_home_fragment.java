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


public class bottom_home_fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private bottom_home_adapter bottom_home_adapter;
    private ArrayList<bottom_home_data> list = new ArrayList<>();
    ViewPager viewPager;
    private FragFirst fragment1;
    private FragSecond fragment2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_home_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);// 리사이클러뷰 id
        list = bottom_home_data.createContactList(5);
        recyclerView.setHasFixedSize(true);
        bottom_home_adapter = new bottom_home_adapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bottom_home_adapter);
        //viewPager = (ViewPager) inflater.inflate(R.layout.home_viewpager,container, false);
        //viewPager = (ViewPager) inflater.inflate(R.layout.home_viewpager2,container, false);
        fragment1 = new FragFirst();
        fragment2 = new FragSecond();
        //viewPager = (ViewPager) viewPager.findViewById(R.id.viewpager);
        //bottom_home_viewAdapter fragmentPagerAdapter = new bottom_home_viewAdapter(getChildFragmentManager());
        //viewPager.setAdapter(fragmentPagerAdapter);
        return view;
    }


 }




