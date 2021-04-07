package com.example.myapplication.register.sns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class TempSnsActivity extends AppCompatActivity {

    private Button btn_sns_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_sns);

        btn_sns_register = findViewById(R.id.btn_sns_register);

        btn_sns_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

    }
}