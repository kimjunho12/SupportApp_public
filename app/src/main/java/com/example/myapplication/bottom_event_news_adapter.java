package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class bottom_event_news_adapter extends RecyclerView.Adapter<bottom_event_news_adapter.CustomViewHolder> {

    private Context context;
    private List<bottom_event_news_model> list = new ArrayList<>();

    public bottom_event_news_adapter(Context context, List<bottom_event_news_model> list) {
        this.context = context;
        this.list = list;
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
        holder.bottom_event_news_model_image.setImageResource(list.get(position).getBottom_event_news_model_image());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭되었을 때 여기다가 구현하기
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView bottom_event_news_model_image;
        //protected TextView bottom_event_news_model_content;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bottom_event_news_model_image = (ImageView) itemView.findViewById(R.id.bottom_event_news_image);
        }
    }
}