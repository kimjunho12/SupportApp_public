package com.example.myapplication.register;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ExpandableListAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.TargetListAdapter;
import com.example.myapplication.adapter2activity;
import com.example.myapplication.models.Subject;
import com.example.myapplication.models.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class SurveyActivity extends AppCompatActivity implements adapter2activity {

    private static final String TAG = "SurveyPage";
    private Button btn_survey_save;
    private TextView btn_survey_skip;
    private RecyclerView recyclerview;
    private ExpandableListAdapter expandableListAdapter;

    private ArrayList<Subject> subjectList = new ArrayList<>();
    private ArrayList<Target> targetList = new ArrayList<>();

    private TargetListAdapter targetListAdapter;
    private ListView searched_target_list;
    private EditText searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // DB에서 후원대상 불러오기
        FirebaseDatabase.getInstance().getReference("target").orderByChild("name")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            targetList.add(new Target(dataSnapshot.child("name").getValue().toString()));
                        }
                        setTargetListAdapter();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        btn_survey_save = findViewById(R.id.btn_survey_save);
        btn_survey_skip = findViewById(R.id.btn_survey_skip);

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
                                subjectList.add(new Subject(Subject.CHILD, childSubject.getValue().toString()));
                            }
                        }

                        expandableListAdapter = new ExpandableListAdapter(subjectList, SurveyActivity.this);
                        recyclerview.setAdapter(expandableListAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        btn_survey_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setText(null);

                String result = "";
                for (Target target : selectTarget) {
                    result += target.getName() + ", ";
                }

                for (Subject subject : selectSubject) {
                    result += subject.text + ", ";
                }
                Log.d(TAG, "onClick: " + result);

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setTargetListAdapter() {
        targetListAdapter = new TargetListAdapter(this, targetList, this);
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
                setListViewHeightBasedOnChildren(searched_target_list);
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setText(null);
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        if (listAdapter.getCount() > 0) {
            View listItem = listAdapter.getView(0, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight = listItem.getMeasuredHeight() * listAdapter.getCount();
        }

        if (listAdapter.getCount() > 0) {
            totalHeight = totalHeight + listView.getPaddingTop() + listView.getPaddingBottom() * 2;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private ArrayList<Subject> selectSubject = new ArrayList<>();
    private ArrayList<Target> selectTarget = new ArrayList<>();

    @Override
    public void addItem(int type, int position) {
        if (type == 1) {
            selectSubject.add(subjectList.get(position));
        } else {
            selectTarget.add(targetList.get(position));
        }
    }

    @Override
    public void deleteItem(int type, int position) {
        if (type == 1) {
            selectSubject.remove(subjectList.get(position));
        } else {
            selectTarget.remove(targetList.get(position));
        }
    }
}