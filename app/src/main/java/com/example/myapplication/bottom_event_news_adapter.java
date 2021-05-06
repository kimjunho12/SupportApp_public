package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.bottom_home_data;

import java.util.ArrayList;
import java.util.List;

public class bottom_event_news_adapter extends RecyclerView.Adapter<bottom_event_news_adapter.CustomViewHolder> {

    private Context context;
    private ArrayList<bottom_home_data> arrayList;

    public bottom_event_news_adapter(ArrayList<bottom_home_data> arrayList, Context context) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public bottom_event_news_adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_event_news, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull bottom_event_news_adapter.CustomViewHolder holder, int position) {
        Glide.with(holder.itemView).load(arrayList.get(position).getImage()).into(holder.image);
        //holder.content.setText(arrayList.get(position).getContent());
        holder.title.setText(arrayList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), newsActivity.class);
                intent.putExtra("title", arrayList.get(position).getTitle());
                intent.putExtra("image", arrayList.get(position).getImage());
                intent.putExtra("content", arrayList.get(position).getContent());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView content;
        protected TextView title;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.bottom_event_news_image);
            //this.content = itemView.findViewById(R.id.content);
            this.title = itemView.findViewById(R.id.bottom_event_news_title);
        }
    }
}