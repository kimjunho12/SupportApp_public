package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.ExpandableListAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivity extends AppCompatActivity {

    private Button btn_survey_save;
    private TextView btn_survey_skip;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        btn_survey_save = findViewById(R.id.btn_sns_register);
        btn_survey_skip = findViewById(R.id.btn_survey_skip);
        
        recyclerview = findViewById(R.id.re_survey_subject);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableListAdapter.Item> data = new ArrayList<>();

        // 나중에는 DB랑 연동해서 불러오자
        // 첫번째 방법 (데이터 리스트에 모아서 data.add(place) 하는 방법 (처음부터 보이는 상태_
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "스포츠"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "축구"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "농구"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "야구"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "음악"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "K-Pop"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "인디밴드"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "힙합"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "등등"));

        // 두번째 방법 바로 place에 header와 invisiblechild로 입력 (처음부터 가려진 상태)
        ExpandableListAdapter.Item places = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "미술");
        places.invisibleChildren = new ArrayList<>();
        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "현대미술"));
        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "고전미술"));

        data.add(places);

        recyclerview.setAdapter(new ExpandableListAdapter(data));


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