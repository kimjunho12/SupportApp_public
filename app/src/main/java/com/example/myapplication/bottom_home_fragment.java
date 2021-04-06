package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class bottom_home_fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private bottom_home_adapter bottom_home_adapter;
    private ArrayList<bottom_home_data> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_home_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        list = bottom_home_data.createContactList(5);
        recyclerView.setHasFixedSize(true);
        bottom_home_adapter = new bottom_home_adapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bottom_home_adapter);
        return view;
    }
}

