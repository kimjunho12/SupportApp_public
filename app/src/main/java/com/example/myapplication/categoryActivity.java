package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.bottom_favorite_profile_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class categoryActivity extends Activity {
    private static final String TAG = "favoritePage";
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter category_adapter;
    private ArrayList<bottom_favorite_profile_model> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private GridLayoutManager gridLayoutManager;
    private FirebaseAuth mAuth;
    private ArrayList<String> categoryList;
    private Intent intent;

    @Nullable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_fragment);
        mAuth = FirebaseAuth.getInstance();
        //recyclerview
        recyclerView = findViewById(R.id.category_recyclerview);// 리사이클러뷰 id
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        int spanCount = 2; // columns
        int spacing = 60; // px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new ItemDecoration(spanCount, spacing, includeEdge));
        //layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(gridLayoutManager);

        intent = getIntent();
        String mCategory = intent.getStringExtra("mCategory");
        TextView textView = findViewById(R.id.category_top_name);
        textView.setText(mCategory + " 후원대상");

        /*// 카테고리목록 불러오기
        categoryList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("target")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            categoryList.add(child.getKey());
                        }
                        loadLikeTarget();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }

                });*/
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        Query query = database.getReference("target").orderByChild("subject/mCategory").equalTo(mCategory);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "Snapshot: " + snapshot.toString());
                    arrayList.add(snapshot.getValue(bottom_favorite_profile_model.class));
                }
                category_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        category_adapter = new category_adapter(arrayList, this);
        recyclerView.setAdapter(category_adapter);

        // 현우형 원본
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                arrayList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    bottom_favorite_profile_model bottom_favorite_profile_model = snapshot.getValue(bottom_favorite_profile_model.class);
//                    bottom_favorite_profile_model.mCategory = snapshot.child("subject").child("mCategory").getValue().toString();
//                    if (bottom_favorite_profile_model.mCategory.equals(mCategory)) {
//                        arrayList.add(bottom_favorite_profile_model);
//                    }
//                    //arrayList.add(bottom_favorite_profile_model);
//                }
//                category_adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("Fraglike", String.valueOf(error.toException())); //에러 시 출력
//            }
//        });
//        category_adapter = new category_adapter(arrayList, this);
//        recyclerView.setAdapter(category_adapter);
    }

    /*public void loadLikeTarget() {
        Intent intent = getIntent();
        String mCategory = intent.getStringExtra("mCategory");
        Log.d(TAG, "error : " + mCategory);

        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("target").child("subject").child("0").child("mCategory");
        for (String category : categoryList) {
            databaseReference.orderByChild("name").equalTo(mCategory).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getValue() != null) {
                            Log.d(TAG, "onDataChange: " + child);
                            bottom_favorite_profile_model bottom_favorite_profile_model = child.getValue(bottom_favorite_profile_model.class);
                            arrayList.add(bottom_favorite_profile_model);
                            category_adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Fraglike", String.valueOf(error.toException())); //에러 시 출력
                }
            });
        }
    }*/
}
