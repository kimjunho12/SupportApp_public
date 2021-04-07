package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class SurveyActivity extends AppCompatActivity {

    private Button btn_survey_save;
    private TextView btn_survey_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        btn_survey_save = findViewById(R.id.btn_sns_register);
        btn_survey_skip = findViewById(R.id.btn_survey_skip);

        btn_survey_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });

        btn_survey_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });


    }
}