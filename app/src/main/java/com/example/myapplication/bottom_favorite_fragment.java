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

import java.util.ArrayList;


public class bottom_favorite_fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private bottom_favorite_profile_adapter bottomFavoriteProfileAdapter;
    private ArrayList<bottom_favorite_profile_model> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_favorite_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.bottom_favorite_recyclerview);
        list = bottom_favorite_profile_model.createContactList(10);
        bottomFavoriteProfileAdapter = new bottom_favorite_profile_adapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bottomFavoriteProfileAdapter);
        return view;
    }
}
