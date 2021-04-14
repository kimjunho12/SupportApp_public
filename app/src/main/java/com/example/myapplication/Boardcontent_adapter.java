package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Boardcontent_adapter extends RecyclerView.Adapter<Boardcontent_adapter.ViewHolder> {

    private ArrayList<Boardcontent_recyclerview> arrayList;

    public Boardcontent_adapter(ArrayList<bottom_home_data> list) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Boardcontent_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acitivity_board_recycle, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Boardcontent_adapter.ViewHolder holder, int position) {
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
