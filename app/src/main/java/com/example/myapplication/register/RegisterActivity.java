package com.example.myapplication.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    private ImageButton btn_back;
    private Button btn_register_save;
    private CheckBox cb_target;

    private EditText et_register_id, et_register_pw, et_register_pw_check, et_register_name, et_register_phone, et_register_birth, et_register_no_check;
    private Button btn_check_id, btn_check_phone;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();

        btn_register_save = findViewById(R.id.btn_register_save);
        btn_back = findViewById(R.id.btn_back);
        cb_target = findViewById(R.id.cb_target_check);

        et_register_id = findViewById(R.id.et_register_id);
        et_register_pw = findViewById(R.id.et_register_pw);
        et_register_pw_check = findViewById(R.id.et_register_pw_check);
        et_register_name = findViewById(R.id.et_register_name);
        et_register_phone = findViewById(R.id.et_register_phone);
        et_register_birth = findViewById(R.id.et_register_birth);
        et_register_no_check = findViewById(R.id.et_register_no_check);

        btn_check_id = findViewById(R.id.btn_check_id);
        btn_check_phone = findViewById(R.id.btn_check_phone);

        mAuth = FirebaseAuth.getInstance();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_check_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 중복확인 로직 필요
                /*
                if 중복
                    Toast
                esle 안중복
                    중복확인버튼(btn_check_id) 체크표시로 변경
                 */
            }
        });


        btn_register_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = String.valueOf(et_register_id.getText());
                String password = String.valueOf(et_register_pw.getText());
                if (email.length() != 0 && password.length() != 0) {
                    createAccount(email, password);
                } else {
                    Toast.makeText(RegisterActivity.this, "정보를 마저 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
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
    }

    private void reload() {
        mAuth.signOut();
    }
}