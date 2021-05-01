package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class newsActivity extends Activity {

    private Intent intent;
    private ImageView imageView;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        imageView = findViewById(R.id.news_detail);
        intent = getIntent();
        

    }
    public void support_button(View view) {
        intent = new Intent(this, profileActivity.class);
        startActivity(intent);
        finish();
    }
}

