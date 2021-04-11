package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ExpandableListAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.TargetListAdapter;
import com.example.myapplication.models.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SurveyActivity extends AppCompatActivity {

    private Button btn_survey_save;
    private TextView btn_survey_skip;
    private RecyclerView recyclerview;

    // 임시
    private TargetListAdapter targetListAdapter;
    private ListView searched_target_list;
    private EditText searchView;

    // end 임시

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // 임시

        ArrayList<Target> targetList = new ArrayList<Target>();
        targetList.add(new Target("김준호"));
        targetList.add(new Target("안부지"));
        targetList.add(new Target("성기준"));
        targetList.add(new Target("문범준"));
        targetList.add(new Target("제갈명원"));
        targetList.add(new Target("황보명선"));
        targetList.add(new Target("안인호"));
        targetList.add(new Target("안상미"));
        targetList.add(new Target("표현수"));
        targetList.add(new Target("손흥민"));
        targetList.add(new Target("정신차려"));
        targetList.add(new Target("고구마"));
        targetList.add(new Target("감자"));
        targetList.add(new Target("치킨"));
        targetList.add(new Target("피자"));


        targetListAdapter = new TargetListAdapter(this, targetList);
        searched_target_list = findViewById(R.id.searched_target_list);
        searched_target_list.setAdapter(targetListAdapter);
        searchView = findViewById(R.id.search_target);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchView.getText().toString().toLowerCase(Locale.getDefault());
                targetListAdapter.filter(text);
                searched_target_list.setVisibility(View.VISIBLE);
            }
        });




        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setText(null);
            }
        });

        
        // 왜 클릭이 안돼 ㅅㅂ
        searched_target_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SurveyActivity.this, "isClicked", Toast.LENGTH_SHORT ).show();
                Log.d("selected_item", "onItemSelected: " + view.getTag() + " + " + i);

            }
        });

        // end 임시

        btn_survey_save = findViewById(R.id.btn_survey_save);
        btn_survey_skip = findViewById(R.id.btn_survey_skip);
        
        recyclerview = findViewById(R.id.re_survey_subject);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableListAdapter.Item> data = new ArrayList<>();

        // 나중에는 DB랑 연동해서 불러오자
        // 첫번째 방법 (데이터 리스트에 모아서 data.add(place) 하는 방법 (처음부터 보이는 상태) : 정적
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

        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Test1"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Test1"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Test1"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Test1"));

        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Test2"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Test2"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Test2"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Test2"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Test2"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Test2"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Test2"));

        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Test3"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Test4"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Test5"));


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