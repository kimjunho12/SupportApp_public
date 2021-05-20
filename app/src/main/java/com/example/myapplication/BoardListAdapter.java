package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
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

    private static final String TAG = "BoardListAdapterPage";
    private ArrayList<Post> arrayList;
    private Intent intent;

    public BoardListAdapter(ArrayList<Post> arrayList) {
        this.arrayList = arrayList;
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

        Post post = arrayList.get(position);
        ViewHolder boardholder = (ViewHolder) holder;

        boardholder.type.setText(arrayList.get(position).getType());
        boardholder.title.setText(arrayList.get(position).getTitle());
        boardholder.username.setText(arrayList.get(position).getUsername());
        boardholder.date.setText(arrayList.get(position).getDate());
        boardholder.cnt.setText(String.valueOf(arrayList.get(position).getView_cnt()));
        
        boardholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BoardcontentActivity.class);
                intent.putExtra("title", arrayList.get(position).getTitle());
                intent.putExtra("contents", arrayList.get(position).getContents());
                intent.putExtra("key", arrayList.get(position).key);
                Log.d(TAG, "onClick: " + arrayList.get(position).key);
                //intent.putExtra("image", arrayList.get(position).getImg())
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView type;
        protected TextView title;
        protected TextView username;
        protected TextView date;
        protected TextView cnt;

        public ViewHolder(@NonNull View view) {
            super(view);

            this.type = (TextView) view.findViewById(R.id.tv_boardlist_type);
            this.title = (TextView) view.findViewById(R.id.tv_boardlist_title);
            this.username = (TextView) view.findViewById(R.id.tv_boardlist_user);
            this.date = (TextView) view.findViewById(R.id.tv_boardlist_date);
            this.cnt = (TextView) view.findViewById(R.id.tv_boardlist_cnt);

        }
    }
}
