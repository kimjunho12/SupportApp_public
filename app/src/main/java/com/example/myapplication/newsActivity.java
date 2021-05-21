package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.myapplication.models.bottom_home_data;

import java.util.ArrayList;

public class newsActivity extends Activity {

    private ArrayList<bottom_home_data> arrayList;
    private Intent intent;
    private View view;


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

        String name = intent.getStringExtra("name");

        Button support_button = findViewById(R.id.support_button);
        support_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), profileActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
            }
        });
    }
}

