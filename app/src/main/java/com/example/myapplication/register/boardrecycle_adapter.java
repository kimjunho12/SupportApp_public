package com.example.myapplication.register;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.bottom_home_data;
import com.example.myapplication.newsActivity;

import java.util.ArrayList;

public class boardrecycle_adapter extends RecyclerView.Adapter<boardrecycle_adapter.ViewHolder> {

    private ArrayList<board_recyclerview> arrayList;

    public boardrecycle_adapter(ArrayList<bottom_home_data> list) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public boardrecycle_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acitivity_board_recycle, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull boardrecycle_adapter.ViewHolder holder, int position) {
        holder.retext.setText(arrayList.get(position).getRetext());
        holder.retext1.setText(arrayList.get(position).getRetext1());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = holder.retext.getText().toString();
                Toast.makeText(v.getContext(), name, Toast.LENGTH_SHORT).show();
            }
        });
        //롱클릭
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int positon) {
        try {
            arrayList.remove(positon);
            notifyItemRemoved(positon);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView retext;
        protected TextView retext1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.retext = (TextView) itemView.findViewById(R.id.retext);
            this.retext1 = (TextView) itemView.findViewById(R.id.retext1);
        }
    }
}
