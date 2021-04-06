package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button btn_signup;
    private Button btn_google;
    private Button btn_fb;
    private Button btn_twt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signup = findViewById(R.id.btn_signup);
        btn_google = findViewById(R.id.btn_google);
        btn_fb = findViewById(R.id.btn_facebook);
        btn_twt = findViewById(R.id.btn_twitter);

        clickBtn(btn_signup);
        clickSNS(btn_google);
        clickSNS(btn_fb);
        clickSNS(btn_twt);
    }

    protected void clickBtn(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 나중에 소셜 로그인은 따로 만들어 주어야함 (각각 API를 통해서)
                Intent intent = new Intent(LoginActivity.this, UserDetailsActivity.class);
                startActivity(intent);  //  activity 이동

                // 회원가입 후 다시 로그인 창으로 돌아오기 위해 finish 보류
                //finish();
            }
        });
    }

    protected void clickSNS(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 나중에 소셜 로그인은 따로 만들어 주어야함 (각각 API를 통해서)
                // 로그인 후 바로 MainActivity로 이동
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);  //  activity 이동

                finish();
            }
        });
    }

}