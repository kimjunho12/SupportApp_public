package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.bottom_event_news_model;

import java.util.ArrayList;


public class bottom_event_fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private bottom_event_news_adapter bottomEventNewsAdapter;
    private ArrayList<bottom_event_news_model> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_event_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.bottom_event_recyclerview);
        list = bottom_event_news_model.createContactList(5);
        recyclerView.setHasFixedSize(true);
        bottomEventNewsAdapter = new bottom_event_news_adapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bottomEventNewsAdapter);
        return view;
    }
}


