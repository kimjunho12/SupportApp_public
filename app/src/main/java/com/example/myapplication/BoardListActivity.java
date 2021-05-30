package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BoardListActivity extends AppCompatActivity {

    private static final String TAG = "BoardListPage";
    private RecyclerView recyclerView;
    private BoardListAdapter boardListAdapter;
    private Button btn_create_post;
    private Intent intent;
    private ArrayList<Post> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);
        recyclerView = findViewById(R.id.re_board_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        intent = getIntent();
        String name = intent.getStringExtra("name1");
        TextView textView = findViewById(R.id.tv_target_name);
        textView.setText(name + " 게시판");

        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("target").child(name).child("post");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post Post = snapshot.getValue(Post.class);
                    Post.key = snapshot.getKey();
                    Post.target = name;
                    Post.type = snapshot.child("type").getValue().toString();
                    //Log.d("값 : ", Post.key + " / " + Post.type + " / " + name);
                    if (Post.type.equals("일반")) {
                        arrayList.add(0, Post);
                    }
                }
                boardListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Fraglike", String.valueOf(error.toException())); //에러 시 출력
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post Post = snapshot.getValue(Post.class);
                    Post.key = snapshot.getKey();
                    Post.target = name;
                    Post.type = snapshot.child("type").getValue().toString();
                    //Log.d("값 : ", Post.key + " / " + Post.type + " / " + name);
                    if (Post.type.equals("공지")) {
                        arrayList.add(0, Post);
                    }
                }
                boardListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Fraglike", String.valueOf(error.toException())); //에러 시 출력
            }
        });

        boardListAdapter = new BoardListAdapter(arrayList);
        recyclerView.setAdapter(boardListAdapter);

        btn_create_post = findViewById(R.id.btn_create_post);
        btn_create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새글쓰기로 이동
                Intent intent = new Intent(BoardListActivity.this, BoardwriteActivity.class);
                intent.putExtra("DB", name);
                startActivity(intent);
            }
        });

        Log.d("Adapters", "onCreate: " + recyclerView + " ::::: " + boardListAdapter.getItemCount());
    }
}