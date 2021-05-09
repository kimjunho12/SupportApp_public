package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.bottom_favorite_profile_model;
import com.example.myapplication.models.bottom_home_data;

import java.util.ArrayList;
import java.util.List;

public class profileActivity extends AppCompatActivity {

    private View view;
    private Intent intent;
    private List<bottom_favorite_profile_model> arrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView textView1 = findViewById(R.id.profile_textView1);
        ImageView imageView = findViewById(R.id.img_profile);
        TextView textView2 = findViewById(R.id.profile_textView2);
        TextView textView3 = findViewById(R.id.profile_textView3);
        TextView textView4 = findViewById(R.id.profile_textView4);
        TextView textView5 = findViewById(R.id.profile_textView5);
        TextView textView6 = findViewById(R.id.profile_textView6);

        intent = getIntent();
        textView1.setText(intent.getStringExtra("birth"));
        textView2.setText(intent.getStringExtra("debut"));
        Glide.with(this).load(intent.getStringExtra("icon")).into(imageView);
        textView3.setText(intent.getStringExtra("intro"));
        textView4.setText(intent.getStringExtra("name"));
        textView5.setText(intent.getStringExtra("sns"));
        textView6.setText(intent.getStringExtra("team"));

        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileActivity.this, BoardListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void profile_support_button(View view) {
        intent = new Intent(this, support_popupActivity.class);
        startActivity(intent);
    }
}
