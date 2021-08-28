package com.example.myapplication.searching;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Target;
import com.example.myapplication.profileActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TargetSearchAdapter extends BaseAdapter {

    private static final String TAG = "TargetSearchAdapterPage";
    Context context;
    LayoutInflater inflater;
    private List<Target> targetList = null;
    private ArrayList<Target> arrayList;


    public TargetSearchAdapter(Context context, List<Target> targetList) {
        this.context = context;
        this.targetList = targetList;
        this.inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Target>();
        this.arrayList.addAll(targetList);
    }

    public class ViewHolder {
        TextView tv_name;
        ImageView iv_icon;
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
        final TargetSearchAdapter.ViewHolder holder;
        final Target target = targetList.get(position);

        if (view == null) {
            view = inflater.inflate(R.layout.target_search_item, null);
            holder = new TargetSearchAdapter.ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            view.setTag(holder);
        } else {
            holder = (TargetSearchAdapter.ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.tv_name.setText(target.getName());

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.d(TAG, "onClick: target is " + target);
                Intent intent = new Intent(arg0.getContext(), profileActivity.class);
                intent.putExtra("birth", targetList.get(position).getBirth());
                intent.putExtra("debut", targetList.get(position).getDebut());
                intent.putExtra("icon", targetList.get(position).getIcon());
                intent.putExtra("intro", targetList.get(position).getIntro());
                intent.putExtra("name", targetList.get(position).getName());
                intent.putExtra("sns", targetList.get(position).getSns());
                intent.putExtra("team", targetList.get(position).getTeam());
                arg0.getContext().startActivity(intent);
                // fragment와 activity 구분해서 종료하는 로직 필요
//                ((Activity) context).finish();
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        targetList.clear();
        if (charText.length() == 0) {
            targetList.clear();
        } else {
            for (Target target : arrayList) {
                String name = target.getName();
                if (name.toLowerCase().contains(charText)) {
                    targetList.add(target);
                }
            }
        }
        notifyDataSetChanged();
    }
}
