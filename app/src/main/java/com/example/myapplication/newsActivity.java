package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class newsActivity extends Activity {

    private Intent intent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        TextView textView1 = findViewById(R.id.textView1);
        ImageView imageView = findViewById(R.id.news_detail);
        TextView textView2 = findViewById(R.id.textView2);

        intent = getIntent();
        textView1.setText(intent.getStringExtra("title"));
        Glide.with(this).load(intent.getStringExtra("image")).into(imageView);
        textView2.setText(intent.getStringExtra("content"));
    }
    public void support_button(View view) {
        intent = new Intent(this, profileActivity.class);
        startActivity(intent);
        finish();
    }
}

