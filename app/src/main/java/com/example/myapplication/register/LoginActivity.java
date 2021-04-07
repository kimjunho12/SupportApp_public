package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.register.sns.TempSnsActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btn_signup;
    private Button btn_google;
    private Button btn_fb;
    private Button btn_twt;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signup = findViewById(R.id.btn_signup);
        btn_google = findViewById(R.id.btn_google);
        btn_fb = findViewById(R.id.btn_facebook);
        btn_twt = findViewById(R.id.btn_twitter);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 세션 처리 후 메인화면으로 이동
                /*
                로그인 세션 처리 및 정보 전달 code 구간
                 */
                // if (로그인 성공 시) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);  //  activity 이동

                finish();
                // }
            }
        });

        clickBtn(btn_signup);
        clickSNS(btn_google);
        clickSNS(btn_fb);
        clickSNS(btn_twt);
    }

    protected void clickBtn(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                // Callback 공부해서 써야해
                startActivityForResult(intent, 0);  //  activity 이동

//                finish();
            }
        });
    }

    protected void clickSNS(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 나중에 소셜 로그인은 따로 만들어 주어야함 (각각 API를 통해서)
                // 로그인 후 바로 MainActivity로 이동
                Intent intent = new Intent(LoginActivity.this, TempSnsActivity.class);
                // Callback 공부해서 써야해
                startActivityForResult(intent, 1);  //  activity 이동

                // 회원가입 후 다시 로그인 창으로 돌아오기 위해 finish 보류
                //finish();
            }
        });
    }

    // 마지막으로 뒤로 가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로 가기 버튼을 누를 때 표시
    private Toast toast;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast 출력
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
            toast = Toast.makeText(this, "이용해 주셔서 감사합니다.", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}