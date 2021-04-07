package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private Button btn_register_save;
    private CheckBox cb_target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();

        btn_register_save = findViewById(R.id.btn_register_save);
        btn_back = findViewById(R.id.btn_back);
        cb_target = findViewById(R.id.cb_target_check);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btn_register_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 후원 대상 체크 시
                if (cb_target.isChecked()) {
                    // setResult 및 Intent 수정 필요
                    setResult(0);
                    Intent intent = new Intent(RegisterActivity.this, UserDetailsActivity.class);
                    startActivity(intent);

                    //콜백 받으면 finish
                    finish();
                }
                // 일반 회원일 경우
                else {

                    //CallBack으로 처리해
//                    Intent intent = new Intent(RegisterActivity.this, SurveyActivity.class);
//                    startActivity(intent);

                    finish();
                }


            }
        });

    }
}