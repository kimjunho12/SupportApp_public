package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.bottom_home_data;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class bottom_event_fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<bottom_home_data> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private GridLayoutManager gridLayoutManager;
    private bottom_event_news_adapter bottom_event_news_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_event_fragment, container, false);

        //recyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.bottom_event_recyclerview);// 리사이클러뷰 id
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        Query query = database.getReference("news").orderByChild("time");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    bottom_home_data bottom_home_data = snapshot.getValue(bottom_home_data.class);
                    arrayList.add(0, bottom_home_data);
                }
                bottom_event_news_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Fraglike", String.valueOf(error.toException())); //에러 시 출력
            }
        });


        bottom_event_news_adapter = new bottom_event_news_adapter(arrayList, getContext());
        recyclerView.setAdapter(bottom_event_news_adapter);
        return view;
    }
}


