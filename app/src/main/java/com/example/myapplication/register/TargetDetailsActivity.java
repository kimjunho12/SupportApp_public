package com.example.myapplication.register;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ExpandableListAdapter;
import com.example.myapplication.R;
import com.example.myapplication.adapter2activity;
import com.example.myapplication.models.Subject;
import com.example.myapplication.models.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class TargetDetailsActivity extends AppCompatActivity implements adapter2activity {

    private static final String TAG = "TargetDetailsPage";
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
    private FirebaseStorage storage;
    private String imagePath;
    private static final int OK = 200;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_details);

        mAuth = FirebaseAuth.getInstance();
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

        btn_change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: before load image mAuth.Uid = " + mAuth.getUid());
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, OK);
            }

        });

        btn_input_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTargetInfo();
                change();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (OK == requestCode && data != null) {
            Log.d(TAG, "onActivityResult: data = " + data);
            imagePath = getPath(data.getData());
            File file = new File(imagePath);
            img_profile.setImageURI(Uri.fromFile(file));
            Log.d(TAG, "onActivityResult: after load image, mAuth.Uid = " + mAuth.getUid());
        }
    }

    public String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    private void updateTargetInfo() {
        if (selectSubject.size() <= 0) {
            Toast.makeText(TargetDetailsActivity.this, "분야를 선택 해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        Target target = new Target(
                String.valueOf(input_name.getText()),
                String.valueOf(input_phone_no.getText()),
                String.valueOf(input_birth_date.getText()),
                String.valueOf(input_sosock.getText()),
                String.valueOf(input_debut_date.getText()),
                String.valueOf(input_SNS.getText()),
                String.valueOf(input_pr.getText()),
                String.valueOf(imagePath)
        );

        for (Subject subject : selectSubject) {
            subject.sCategory = String.valueOf(input_sosock.getText());
        }

        DatabaseReference mDBRefer = FirebaseDatabase.getInstance().getReference("target").child(mAuth.getUid());
        mDBRefer.setValue(target);
        mDBRefer.child("subject").setValue(selectSubject.size() > 0 ? selectSubject.get(0) : null);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());
        if(imagePath == null) {
            databaseReference.child("photoURL").setValue("null");
        }
        else {
            databaseReference.child("photoURL").setValue(imagePath);
        }
        setResult(OK);
        Toast.makeText(TargetDetailsActivity.this, "후원대상 상세정보 입력이 완료 되었습니다.\n로그인을 진행 해 주세요.", Toast.LENGTH_LONG).show();
        finish();
    }

    private void change() {
        Log.d(TAG, "change: imagePath = " + imagePath);
        if (imagePath == null) {
            return;
        }
        Log.d(TAG, "change: start");
        File file = new File(imagePath);
        String strFileName = file.getName();
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://supportapp-f34a1.appspot.com");
        StorageReference storageReference = storage.getReference();
        storageReference.child("images/" + strFileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // 실행 안됨 & Storage 업로드 또한 안됨
                Log.d(TAG, "img uri : " + uri);
                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference().child("target").child(mAuth.getUid()).child("icon");
                databaseReference.setValue(uri.toString()); // Permission denied
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
            }
        });
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
        Log.d(TAG, "init: mAuth = " + mAuth);
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