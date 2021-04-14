package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.models.Post;

import java.util.ArrayList;
import java.util.Date;

public class BoardListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BoardListAdapter boardListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        Date date = new Date();
        ArrayList<Post> boardlist = new ArrayList<>();
        boardlist.add(new Post(0, "공지사항", "관리자"));
        boardlist.add(new Post(0, "긴급 공지", "관리자"));
        boardlist.add(new Post(1, "둘둘말이김밥", "추", date, 0));
        boardlist.add(new Post(0, "--안내--", "adsa"));
        boardlist.add(new Post(1, "기무닝ㅁㄴ입ㅈ우", "czxcaqq"));
        boardlist.add(new Post(1, "ddqwzxcasdasd!@#$%^&*()_+", "123d1d"));
        boardlist.add(new Post(1, "아", "13adasdasd"));
        boardlist.add(new Post(1, " ", "1dcv"));
        boardlist.add(new Post(1, "가나다라마바사아자차카타파하", "김준호"));
        boardlist.add(new Post(1, "준", "정"));
        boardlist.add(new Post(1, "12345", "정진", date, 11));
        boardlist.add(new Post(1, "미티치미ㅣㅣ이빚디비디", "감식", date, 120));

        boardListAdapter = new BoardListAdapter(boardlist);
        recyclerView = findViewById(R.id.re_board_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(boardListAdapter);

        Log.d("Adapters", "onCreate: " + recyclerView + " ::::: " + boardListAdapter.getItemCount());
    }
}