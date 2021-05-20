package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.Boardcontent_data;
import com.example.myapplication.models.Post;

import java.util.ArrayList;

public class Boardcontent_adapter extends RecyclerView.Adapter<Boardcontent_adapter.ViewHolder> {

    private ArrayList<Boardcontent_data> arrayList;

    public Boardcontent_adapter(ArrayList<Boardcontent_data> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Boardcontent_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acitivity_board_content_recycle, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Boardcontent_adapter.ViewHolder holder, int position) {
        Boardcontent_data boardcontent_data = arrayList.get(position);
        Boardcontent_adapter.ViewHolder viewHolder = (Boardcontent_adapter.ViewHolder) holder;

        viewHolder.name.setText(arrayList.get(position).getName());
        viewHolder.reply.setText(arrayList.get(position).getReply());

        /*//롱클릭
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    /*public void addItem(Boardcontent_data item) {
        arrayList.add(item);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<Boardcontent_data> items) {
        this.arrayList = items;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView reply;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.reply = (TextView) itemView.findViewById(R.id.reply);
        }
    }
}
