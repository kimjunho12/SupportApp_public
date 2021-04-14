package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.Post;

import java.util.ArrayList;

public class BoardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Post> data;

    public BoardListAdapter(ArrayList<Post> data) {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView type;
        TextView title;
        TextView username;
        TextView date;
        TextView cnt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.tv_boardlist_type);
            title = itemView.findViewById(R.id.tv_boardlist_title);
            username = itemView.findViewById(R.id.tv_boardlist_user);
            date = itemView.findViewById(R.id.tv_boardlist_date);
            cnt = itemView.findViewById(R.id.tv_boardlist_cnt);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.board_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = data.get(position);
        ViewHolder boardholder = (ViewHolder) holder;

        boardholder.type.setText(Integer.toString(post.type));
        boardholder.title.setText(post.title);
        boardholder.username.setText(post.username);
        // 밑에 두개는 제대로 받아서 보내주는 식으로
        boardholder.date.setText("21.04.13");
        boardholder.cnt.setText("조회 " + "0");

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
