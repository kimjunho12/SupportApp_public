package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.models.Target;
import com.google.firebase.database.FirebaseDatabase;

public class TargetDetailsActivity extends AppCompatActivity {

    private Button btn_input_save;
    private EditText input_name;
    private EditText input_phone_no;
    private EditText input_birth_date;
    private EditText input_sosock;
    private EditText input_debut_date;
    private EditText input_SNS;
    private EditText input_pr;
    private ImageView img_profile;
    private TextView btn_change_profile;
    private static final int OK = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_details);

        btn_input_save = findViewById(R.id.btn_details_save);

        init();

        btn_input_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTargetInfo();
            }
        });
    }

    private void updateTargetInfo() {
        Target target = new Target(
                String.valueOf(input_name.getText()),
                String.valueOf(input_phone_no.getText()),
                String.valueOf(input_birth_date.getText()),
                String.valueOf(input_sosock.getText()),
                String.valueOf(input_debut_date.getText()),
                String.valueOf(input_SNS.getText()),
                String.valueOf(input_pr.getText()));

        FirebaseDatabase.getInstance().getReference("target").push().setValue(target);
        setResult(OK);
        finish();
    }

    private void init() {
        input_name = findViewById(R.id.input_name);
        input_phone_no = findViewById(R.id.input_phone_no);
        input_birth_date = findViewById(R.id.input_birth_date);
        input_sosock = findViewById(R.id.input_sosock);
        input_debut_date = findViewById(R.id.input_debut_date);
        input_SNS = findViewById(R.id.input_SNS);
        input_pr = findViewById(R.id.input_pr);
        img_profile = findViewById(R.id.img_profile);
        btn_change_profile = findViewById(R.id.btn_change_profile);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            input_name.setText(intent.getStringExtra("name"));
            input_phone_no.setText(intent.getStringExtra("phone"));
            input_birth_date.setText(intent.getStringExtra("birth"));
        }
    }
}