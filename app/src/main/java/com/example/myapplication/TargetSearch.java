//package com.example.myapplication;
//
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.myapplication.models.Target;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.Locale;
//
//public class TargetSearch extends AppCompatActivity {
//
//    private ArrayList<Target> targetList = new ArrayList<>();
//
//    private TargetListAdapter targetListAdapter;
//    private ListView searched_target_list;
//    private EditText searchView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.search_view);
//    }
//
//    public void loadTarget() {
//        // DB에서 후원대상 불러오기
//        FirebaseDatabase.getInstance().getReference("target").orderByChild("name")  // 나중에는 orderbychild 붙여서
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            targetList.add(new Target(dataSnapshot.child("name").getValue().toString()));
//                        }
//                        setTargetListAdapter();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                    }
//                });
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        View focusView = getCurrentFocus();
//        if (focusView != null) {
//            Rect rect = new Rect();
//            focusView.getGlobalVisibleRect(rect);
//            int x = (int) ev.getX(), y = (int) ev.getY();
//            if (!rect.contains(x, y)) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                if (imm != null)
//                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
//                focusView.clearFocus();
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//    public void setTargetListAdapter() {
//        targetListAdapter = new TargetListAdapter(this, targetList);
//        searched_target_list = findViewById(R.id.searched_target_list);
//        searched_target_list.setAdapter(targetListAdapter);
//        searchView = findViewById(R.id.search_target);
//
//        searchView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String text = searchView.getText().toString().toLowerCase(Locale.getDefault());
//                targetListAdapter.filter(text);
//                searched_target_list.setVisibility(View.VISIBLE);
//                setListViewHeightBasedOnChildren(searched_target_list);
//            }
//        });
//
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchView.setText(null);
//            }
//        });
//    }
//
//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = 0;
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
//        if (listAdapter.getCount() > 0) {
//            View listItem = listAdapter.getView(0, null, listView);
//            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight = listItem.getMeasuredHeight() * listAdapter.getCount();
//        }
//
//        if (listAdapter.getCount() > 0) {
//            totalHeight = totalHeight + listView.getPaddingTop() + listView.getPaddingBottom() * 2;
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight;
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//    }
//}