package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.myapplication.models.Post;
import com.example.myapplication.register.LoginActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardwriteActivity extends AppCompatActivity {

    private ImageView imageView;
    private static final int GET_GALLARY = 100;
    private TextView et_board_write_title;
    private TextView et_board_write_contents;
    private Button btn_board_add_photo;
    private Button btn_create_post;
    private Switch sw_board_type;
    private String target;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        target = intent.getStringExtra("DB");

        et_board_write_title = findViewById(R.id.et_board_write_title);
        et_board_write_contents = findViewById(R.id.et_board_write_contents);
        btn_create_post = findViewById(R.id.btn_create_post);
        sw_board_type = findViewById(R.id.sw_board_type);
        btn_board_add_photo = (Button) findViewById(R.id.btn_board_add_photo);
        imageView = (ImageView) findViewById(R.id.imageView);

        storage = FirebaseStorage.getInstance();
        //사진
        btn_board_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사진첨부 로직
               Intent intent = new Intent(String.valueOf(StorageActivity.class));
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(intent, GET_GALLARY);

            }
        });

        btn_create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                if (sw_board_type.isChecked()) {
                            post.type = "공지";
                } else {
                            post.type = "일반";
                }
                post.title = String.valueOf(et_board_write_title.getText());
                post.username = mAuth.getCurrentUser().getEmail();      // 나중에 닉네임으로 나오게끔
                post.date = new SimpleDateFormat("MM-dd hh:mm").format(new Date(System.currentTimeMillis()));
                post.view_cnt = 0;
                post.contents = String.valueOf(et_board_write_contents.getText());
                post.img = "null";

                FirebaseDatabase.getInstance().getReference("target").child(target).child("post").push().setValue(post);
                setResult(200);
                finish();
                    }
                });
            }
        }
