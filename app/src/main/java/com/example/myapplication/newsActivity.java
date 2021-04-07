package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class newsActivity extends Activity {

    private Intent intent;
    private ImageView imageView;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news); //전환된 xml
        intent = getIntent();
        imageView = findViewById(R.id.bam);

        imageView = findViewById(R.id.banner);
        imageView = findViewById(R.id.banner1);


    }
}

