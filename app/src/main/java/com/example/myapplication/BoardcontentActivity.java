package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.Boardcontent_data;

import java.util.ArrayList;

public class BoardcontentActivity extends AppCompatActivity {

    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Boardcontent_data> arrayList;
    private Boardcontent_data boardcontentRecyclerview;
    private Boardcontent_adapter boardcontent_adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        //recyclerView
        recyclerView = (RecyclerView)findViewById(R.id.board_recycle);
        boardcontent_adapter = new Boardcontent_adapter(arrayList);
        arrayList = new ArrayList<>();

        Boardcontent_data boardcontent_data = new Boardcontent_data("댓글이 없습니다", "댓글이 없습니다");
        arrayList.add(boardcontent_data);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(boardcontent_adapter);
        Button btn_board = findViewById(R.id.btn_board);
        btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boardcontent_data boardcontent_data = new Boardcontent_data("댓글", "댓글1");
                arrayList.add(boardcontent_data);
                boardcontent_adapter.notifyDataSetChanged();
            }
        });


    }
}