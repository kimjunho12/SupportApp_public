package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.models.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TargetListAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    private List<Target> targetList = null;
    private ArrayList<Target> arrayList;
    public TargetListAdapter(Context context, List<Target> targetList) {
        this.context = context;
        this.targetList = targetList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Target>();
        this.arrayList.addAll(targetList);
    }

    public class ViewHolder {
        TextView tv_name;
        ImageView iv_icon;
        CheckBox cb_target;
    }

    @Override
    public int getCount() {
        return targetList.size();
    }

    @Override
    public Target getItem(int position) {
        return targetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final Target target = targetList.get(position);


        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_listview, null);
            // Locate the TextViews in listview_item.xml
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            holder.cb_target = (CheckBox) view.findViewById(R.id.picked_target);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.tv_name.setText(target.name);
//        holder.iv_icon.setImageIcon (target.icon); // 좀 이상해
//        Glide.with(context).load(target.icon).into(holder.iv_icon);

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                Intent intent = new Intent(context, BrewingActivity.class);;
//                ...
//                context.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        targetList.clear();
        if (charText.length() == 0) {
            targetList.addAll(arrayList);
        } else {
            for (Target target : arrayList) {
                String name = target.name;
                if (name.toLowerCase().contains(charText)) {
                    targetList.add(target);
                }
            }
        }
        notifyDataSetChanged();
    }

}