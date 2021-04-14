package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class profileActivity extends AppCompatActivity {

    private View view;
    private Intent intent;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private profileActivity_adapter profileActivityAdapter;
    private ArrayList<bottom_home_data> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        intent = getIntent();
        imageView = findViewById(R.id.img_profile);
        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.profile_recycler);
        list = bottom_home_data.createContactList(5);
        profileActivityAdapter = new profileActivity_adapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(profileActivityAdapter);
    }
    public void profile_support_button(View view) {
        intent = new Intent(this, support_popupActivity.class);
        startActivity(intent);
        //startActivityForResult(intent, 1);
        //intent.putExtra("data", "Test Popup");
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                String result = data.getStringExtra("result");
                txtResult.setText(result);
            }
        }
    }*/
}
