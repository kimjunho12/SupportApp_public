package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ExpandableListAdapter;
import com.example.myapplication.R;
import com.example.myapplication.adapter2activity;
import com.example.myapplication.models.Subject;
import com.example.myapplication.models.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TargetDetailsActivity extends AppCompatActivity implements adapter2activity {

    private Button btn_input_save;
    private EditText input_name;
    private EditText input_phone_no;
    private EditText input_birth_date;
    private EditText input_sosock;
    private EditText input_debut_date;
    private EditText input_SNS;
    private EditText input_pr;
    private RecyclerView recyclerview;
    private ArrayList<Subject> subjectList = new ArrayList<>();
    private ExpandableListAdapter expandableListAdapter;
    private ImageView img_profile;
    private TextView btn_change_profile;
    private static final int OK = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_details);

        btn_input_save = findViewById(R.id.btn_details_save);

        init();

        recyclerview = findViewById(R.id.re_survey_subject);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // DB에서 분야 불러오기
        FirebaseDatabase.getInstance().getReference("Subject").orderByKey()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot parentSubject : snapshot.getChildren()) {
                            subjectList.add(new Subject(Subject.HEADER, parentSubject.getKey()));

                            for (DataSnapshot childSubject : parentSubject.getChildren()) {
                                Subject mSubject = new Subject(Subject.CHILD, childSubject.getValue().toString());
                                mSubject.lCategory = parentSubject.getKey();
                                subjectList.add(mSubject);
                            }
                        }

                        expandableListAdapter = new ExpandableListAdapter(subjectList, TargetDetailsActivity.this);
                        recyclerview.setAdapter(expandableListAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });



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

        for (Subject subject : selectSubject) {
            subject.sCategory = String.valueOf(input_sosock.getText());
        }

        DatabaseReference mDBRefer = FirebaseDatabase.getInstance().getReference("target").push();
        mDBRefer.setValue(target);
        mDBRefer.child("subject").setValue(selectSubject.get(0));
        setResult(OK);
        Toast.makeText(TargetDetailsActivity.this, "후원대상 상세정보 입력이 완료 되었습니다.\n로그인을 진행 해 주세요.", Toast.LENGTH_LONG).show();
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
    private ArrayList<Subject> selectSubject = new ArrayList<>();

    @Override
    public void addItem(int type, int position) {
        selectSubject.add(subjectList.get(position));
    }

    @Override
    public void deleteItem(int type, int position) {
        selectSubject.remove(subjectList.get(position));
    }
}