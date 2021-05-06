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
import com.example.myapplication.models.bottom_favorite_profile_model;

import java.util.ArrayList;
import java.util.List;

public class bottom_favorite_profile_adapter extends RecyclerView.Adapter<bottom_favorite_profile_adapter.CustomViewHolder> {

    private Context context;
    private List<bottom_favorite_profile_model> arrayList;

    public bottom_favorite_profile_adapter(ArrayList<bottom_favorite_profile_model> arrayList, Context context) {
        this.context = context;
        this.arrayList = arrayList;
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
        Glide.with(holder.itemView).load(arrayList.get(position).getProfile_image()).into(holder.profile_image);
        holder.profile_name.setText(arrayList.get(position).getProfile_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), profileActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView profile_image;
        protected TextView profile_name;

        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.profile_image = (ImageView) view.findViewById(R.id.profile_image);
            this.profile_name = (TextView) view.findViewById(R.id.profile_name);
        }
    }
}
