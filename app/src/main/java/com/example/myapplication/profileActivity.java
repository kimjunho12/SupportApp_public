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
import com.example.myapplication.models.bottom_home_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class profileActivity extends AppCompatActivity {

    private static final String TAG = "ProfilePage";
    private View view;
    private Intent intent;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<bottom_home_data> arrayList2;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter profile_adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        intent = getIntent();
        String name = intent.getStringExtra("name");

        TextView tv_profile_birth = findViewById(R.id.tv_profile_birth);
        ImageView imageView = findViewById(R.id.img_profile);
        TextView tv_profile_debut = findViewById(R.id.tv_profile_debut);
        TextView tv_profile_intro = findViewById(R.id.tv_profile_intro);
        TextView tv_profile_name = findViewById(R.id.tv_profile_name);
        TextView tv_profile_sns = findViewById(R.id.tv_profile_sns);
        TextView tv_profile_team = findViewById(R.id.tv_profile_team);


        database = FirebaseDatabase.getInstance();
        database.getReference("target").orderByChild("name")
                .equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot this_target : snapshot.getChildren()) {
                    String birth = this_target.child("birth").getValue().toString();
                    String debut = this_target.child("debut").getValue().toString();
                    String icon = this_target.child("icon").getValue().toString();
                    String intro = this_target.child("intro").getValue().toString();
                    String sns = this_target.child("sns").getValue().toString();
                    String team = this_target.child("team").getValue().toString();

                    tv_profile_birth.setText(birth);
                    tv_profile_debut.setText(debut);
                    tv_profile_intro.setText(intro);
                    tv_profile_name.setText(name);
                    tv_profile_sns.setText(sns);
                    tv_profile_team.setText(team);
                    Glide.with(profileActivity.this).load(icon).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        Button btn_profile_like = findViewById(R.id.btn_profile_like);
        btn_profile_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikeClicked(mAuth.getUid(), name);
            }
        });


        Button btn_profile_board = findViewById(R.id.btn_profile_board);
        btn_profile_board.setOnClickListener(new View.OnClickListener() {
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


        Button btn2 = findViewById(R.id.btn_profile_dona);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileActivity.this, support_popupActivity.class);
                intent.putExtra("name1", name);
                startActivity(intent);
            }
        });
    }

    public void onLikeClicked(String uid, String target) {
        FirebaseDatabase.getInstance().getReference("target").child(target)
                .child("subject").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    mDatabase.child(uid).child("like").child(dataSnapshot.child("lCategory").getValue().toString()).setValue(true);
                    mDatabase.child(uid).child("like").child(dataSnapshot.child("mCategory").getValue().toString()).setValue(true);
                    mDatabase.child(uid).child("like").child(dataSnapshot.child("sCategory").getValue().toString()).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        mDatabase.child(uid).child("like").child(target).setValue(true);
    }
}
