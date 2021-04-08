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

import java.util.ArrayList;
import java.util.List;

public class bottom_home_adapter extends RecyclerView.Adapter<bottom_home_adapter.CustomViewHolder> {
    private Context context;
    private List<bottom_home_data> list = new ArrayList<>();


    public bottom_home_adapter(Context context, List<bottom_home_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public bottom_home_adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_home_recyclerview, parent, false);
        bottom_home_adapter.CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.news_image1.setImageResource(list.get(position).getBanner());
        holder.news_image2.setImageResource(list.get(position).getBanner1());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(view.getContext(), newsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

@Override
public int getItemCount() {
        return list.size();
        }


class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView news_image1;
        protected ImageView news_image2;
        protected TextView id;
        protected TextView content;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.news_image1 = (ImageView) itemView.findViewById(R.id.news_image1);
            this.news_image2 = (ImageView) itemView.findViewById(R.id.news_image2);
        }
    }
}






