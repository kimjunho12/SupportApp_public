package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.register.board_recyclerview;
import com.example.myapplication.register.boardrecycle_adapter;

import java.util.ArrayList;

public class profileActivity extends AppCompatActivity {

    private View view;
    private Intent intent;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private boardrecycle_adapter boardrecycle_adapter;
    private board_recyclerview board_recyclerview;
    private profileActivity_adapter profileActivityAdapter;
    private ArrayList<bottom_home_data> list = new ArrayList<>();
    private ArrayList<board_recyclerview> arrayList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        intent = getIntent();
        imageView = findViewById(R.id.img_profile);
        //recyclerView news
        recyclerView = (RecyclerView) findViewById(R.id.profile_recycler);
        list = bottom_home_data.createContactList(5);
        profileActivityAdapter = new profileActivity_adapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(profileActivityAdapter);
        // 게시판 리사이클러뷰
//        recyclerView = (RecyclerView) findViewById(R.id.board_recycle);
//        linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        arrayList = new ArrayList<>();
//
//        boardrecycle_adapter = new boardrecycle_adapter(list);
//        recyclerView.setAdapter(boardrecycle_adapter);
//
//        Button btn_board = (Button)findViewById(R.id.btn_board);
//        btn_board.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                board_recyclerview board_recyclerview = new board_recyclerview("댓글", "댓글1");
//                arrayList.add(board_recyclerview);
//                boardrecycle_adapter.notifyDataSetChanged();
//            }
//
//            });
        }
    //후원하기 버튼
    public void profile_support_button(View view) {
        intent = new Intent(this, support_popupActivity.class);
        startActivity(intent);
        //startActivityForResult(intent, 1);
        //intent.putExtra("data", "Test Popup");
    }
    //게시판 버튼 누르면 게시판 이동
//    public void profile_board_button(View view){
//        intent = new Intent(this,.class);
//        startActivity(intent);
//    }
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
