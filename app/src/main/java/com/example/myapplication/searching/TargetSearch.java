package com.example.myapplication.searching;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.models.Target;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class TargetSearch extends NavigationView {

    private static final String TAG = "TargetSearchPage";
    private Context mContext;
    private ArrayList<Target> targetList = new ArrayList<>();

    public View searchDrawer;
    private TargetSearchAdapter targetSearchAdapter;
    private ListView searched_target_list;
    private EditText searchView;
    private com.example.myapplication.adapter2activity adapter2activity;

    public TargetSearch(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        searchDrawer = ((Activity) mContext).findViewById(R.id.search_drawer);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_view, this, true);
        loadTarget();
        Log.d(TAG, "init: is inited");
    }


    public void loadTarget() {
        // DB에서 후원대상 불러오기
        FirebaseDatabase.getInstance().getReference("target").orderByChild("name")  // 나중에는 orderbychild 붙여서
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
        Log.d(TAG, "loadTarget: is loaded");
    }

    public void setTargetListAdapter() {
        targetSearchAdapter = new TargetSearchAdapter(getContext(), targetList);
        searched_target_list = ((Activity) mContext).findViewById(R.id.searched_target_list);
        searched_target_list.setAdapter(targetSearchAdapter);
        searchView = ((Activity) mContext).findViewById(R.id.search_target);

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
                targetSearchAdapter.filter(text);
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
        Log.d(TAG, "setTargetListAdapter: is adapted");
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
}