package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.Boardcontent_data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class BoardcontentActivity extends AppCompatActivity {

    private static final String TAG = "BoardcontentPage";
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Boardcontent_data> arrayList;
    private Boardcontent_adapter boardcontent_adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ImageView imageView;
    private FirebaseStorage storage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        Intent intent = getIntent();
        String name = intent.getStringExtra("target");
        String key = intent.getStringExtra("key");
        String img = intent.getStringExtra("img");
        File file = new File(img);
        String strFileName = file.getName();
        Log.d("TEST", strFileName);

        mAuth = FirebaseAuth.getInstance();

        //recyclerView
        recyclerView = (RecyclerView)findViewById(R.id.board_recycle);

        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("target").child(name).child("post").child(key).child("reply");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Boardcontent_data boardcontent_data = snapshot.getValue(Boardcontent_data.class);
                    arrayList.add(0, boardcontent_data);
                }
                boardcontent_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Fraglike", String.valueOf(error.toException())); //에러 시 출력
            }
        });

        boardcontent_adapter = new Boardcontent_adapter(arrayList);
        recyclerView.setAdapter(boardcontent_adapter);


        TextView textView1 = findViewById(R.id.tv_board_title);
        TextView textView2 = findViewById(R.id.tv_board_contents);
        TextView textView3= findViewById(R.id.board_top_name);
        ImageView imageView = findViewById(R.id.tv_imageView);

        textView1.setText(intent.getStringExtra("title"));
        textView2.setText(intent.getStringExtra("contents"));
        textView3.setText(name + " 게시판");

        //Glide.with(BoardcontentActivity.this).load(img).into(imageView);
        EditText reply = findViewById(R.id.et_reply);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://supportapp-f34a1.appspot.com");
        StorageReference storageReference = storage.getReference();
        storageReference.child("images/" + strFileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "img uri : " + uri);
                Glide.with(BoardcontentActivity.this).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        boardcontent_adapter = new Boardcontent_adapter(arrayList);
        recyclerView.setAdapter(boardcontent_adapter);


        Button btn_board = findViewById(R.id.btn_board);
        btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boardcontent_data boardcontent_data = new Boardcontent_data(mAuth.getCurrentUser().getEmail(), String.valueOf(reply.getText()));
                databaseReference.push().setValue(boardcontent_data);
                reply.setText(null);
            }
        });
    }
}