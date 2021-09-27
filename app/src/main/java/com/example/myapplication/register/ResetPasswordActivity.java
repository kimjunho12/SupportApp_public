package com.example.myapplication.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ResetPage";
    private EditText et_reset_id, et_reset_pw, et_reset_pw_check;
    private Button btn_send_reset_mail, btn_reset_save;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();

        et_reset_id = findViewById(R.id.et_reset_id);
        et_reset_pw = findViewById(R.id.et_reset_pw);
        et_reset_pw_check = findViewById(R.id.et_reset_pw_check);

        btn_send_reset_mail = findViewById(R.id.btn_send_reset_mail);
        btn_reset_save = findViewById(R.id.btn_reset_save);

        btn_send_reset_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect();
            }
        });
    }

    private void redirect() {
        String email = String.valueOf(et_reset_id.getText().toString());
        if (isValidEmail(email)) {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "해당 메일로 비밀번호 초기화 메일이 전송 되었습니다.", Toast.LENGTH_LONG).show();
                        btn_send_reset_mail.setEnabled(false);
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "가입하신 이메일을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(ResetPasswordActivity.this, "이메일 주소를 확인 해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        return err;
    }
}