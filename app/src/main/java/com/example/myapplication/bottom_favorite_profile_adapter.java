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

public class bottom_favorite_profile_adapter extends RecyclerView.Adapter<bottom_favorite_profile_adapter.CustomViewHolder> {

    private Context context;
    private List<bottom_favorite_profile_model> list = new ArrayList<>();

    public bottom_favorite_profile_adapter(Context context, List<bottom_favorite_profile_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public bottom_favorite_profile_adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_favorite_profile, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull bottom_favorite_profile_adapter.CustomViewHolder holder, int position) {
        holder.bottom_favorite_profile_image1.setImageResource(list.get(position).getBottom_favorite_profile_image1());
        holder.blank_text.setText(list.get(position).getBlank_text());
        holder.bottom_favorite_profile_image2.setImageResource(list.get(position).getBottom_favorite_profile_image2());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), profileActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView bottom_favorite_profile_image1;
        protected TextView blank_text;
        protected ImageView bottom_favorite_profile_image2;


        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.bottom_favorite_profile_image1 = (ImageView) view.findViewById(R.id.bottom_favorite_profile1);
            this.blank_text = (TextView) view.findViewById(R.id.blank_text);
            this.bottom_favorite_profile_image2 = (ImageView) view.findViewById(R.id.bottom_favorite_profile2);
        }
    }
}
