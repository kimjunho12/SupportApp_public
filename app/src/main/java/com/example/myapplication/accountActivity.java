//package com.example.myapplication;
//
//import android.Manifest;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.loader.content.CursorLoader;
//
//import com.example.myapplication.models.User;
//import com.example.myapplication.models.bottom_favorite_profile_model;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.io.File;
//
//public class accountActivity extends AppCompatActivity {
//
//        private ImageView imageView;
//        private static final int GET_GALLARY = 100;
//        private String target;
//        private FirebaseAuth mAuth;
//        private FirebaseStorage storage;
//        private FirebaseDatabase database;
//        private DatabaseReference mDatabase;
//        private String imagePath;
//        private Button bottom_event_fragment_image_change_profile;
//        private Button bottom_event_fragment_details_save;
//        private TextView input_name;
//        private TextView input_phone_no;
//        private TextView input_birth_date;
//        private TextView input_sosock;
//        private TextView input_debut_date;
//        private TextView input_SNS;
//        private TextView input_pr;
//        private Intent intent;
//        private String img;
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.bottom_account_fragment);
//
//            mAuth = FirebaseAuth.getInstance();
//            storage = FirebaseStorage.getInstance();
//            database = FirebaseDatabase.getInstance();
//            mDatabase = FirebaseDatabase.getInstance().getReference();
//
//
//            Intent intent = getIntent();
//            target = intent.getStringExtra("DB");
//
//
//            //사진
//        bottom_event_fragment_image_change_profile.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                    startActivityForResult(intent, GET_GALLARY);
//                }
//
//            });
//        bottom_event_fragment_details_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//    }
//
//
//}}
//
//
//
