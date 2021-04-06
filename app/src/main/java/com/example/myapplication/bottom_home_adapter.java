package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class bottom_home_adapter extends  RecyclerView.Adapter<bottom_home_adapter.CustomViewHolder> {
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
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull bottom_home_adapter.CustomViewHolder holder, int position) {
        holder.banner.setImageResource(list.get(position).getBanner());
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView banner;
        protected TextView id;
        protected TextView content;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.banner = (ImageView) itemView.findViewById(R.id.banner);
        }
    }
}






