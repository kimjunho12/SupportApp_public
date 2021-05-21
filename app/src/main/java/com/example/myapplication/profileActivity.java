package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.Target;
import com.example.myapplication.models.bottom_home_data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class profileActivity extends AppCompatActivity {

    private View view;
    private Intent intent;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<bottom_home_data> arrayList2;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter profile_adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        intent = getIntent();
        String name = intent.getStringExtra("name");

        TextView textView1 = findViewById(R.id.profile_textView1);
        ImageView imageView = findViewById(R.id.img_profile);
        TextView textView2 = findViewById(R.id.profile_textView2);
        TextView textView3 = findViewById(R.id.profile_textView3);
        TextView textView4 = findViewById(R.id.profile_textView4);
        TextView textView5 = findViewById(R.id.profile_textView5);
        TextView textView6 = findViewById(R.id.profile_textView6);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("target").child(name).child("birth");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String birth = dataSnapshot.getValue().toString();
                textView1.setText(birth);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        /*databaseReference = database.getReference("target").child(name).child("icon");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String icon = dataSnapshot.getValue().toString();
                Glide.with(this).load(icon).into(imageView);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });*/

        databaseReference = database.getReference("target").child(name).child("debut");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String debut = dataSnapshot.getValue().toString();
                textView2.setText(debut);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        databaseReference = database.getReference("target").child(name).child("intro");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String intro = dataSnapshot.getValue().toString();
                textView3.setText(intro);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        textView4.setText(name);

        databaseReference = database.getReference("target").child(name).child("sns");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sns = dataSnapshot.getValue().toString();
                textView5.setText(sns);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        databaseReference = database.getReference("target").child(name).child("team");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String team = dataSnapshot.getValue().toString();
                textView6.setText(team);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });


        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileActivity.this, BoardListActivity.class);
                intent.putExtra("name1", name);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.profile_recycler);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        int spanCount = 2; // columns
        int spacing = 60; // px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new ItemDecoration(spanCount, spacing, includeEdge));

        arrayList2 = new ArrayList<>();
        databaseReference = database.getReference("target").child(name).child("news");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList2.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    bottom_home_data bottom_home_data = snapshot.getValue(bottom_home_data.class);
                    arrayList2.add(bottom_home_data);
                }
                profile_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Fraglike", String.valueOf(error.toException())); //에러 시 출력
            }
        });
        profile_adapter = new bottom_home_adapter(arrayList2, this);
        recyclerView.setAdapter(profile_adapter);
    }

    public void profile_support_button(View view) {
        intent = new Intent(this, support_popupActivity.class);
        startActivity(intent);
    }
}
