package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.models.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<Item> data;
    HashMap<String, Boolean> hm = new HashMap<String, Boolean>();

    public ExpandableListAdapter(List<Item> data) {
        this.data = data;

        for (Item check_subject : data) {
            hm.put(check_subject.text, false);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandable_list_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                LayoutInflater child_inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = child_inflater.inflate(R.layout.search_target_listview, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(view);
                return child;
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.text);
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                            item.invisibleChildren = null;
                        }
                    }
                });
                itemController.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder childController = (ListChildViewHolder) holder;
                childController.child_title.setText(data.get(position).text);

                if (hm.get(item.text) == null) {
                    hm.put(item.text, false);
                }
                if (hm.get(item.text)) {
                    childController.child_cb.setChecked(true);
                } else {
                    childController.child_cb.setChecked(false);
                }

                childController.child_cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.picked_target);
                        if (checkBox.isChecked()) {
                            checkBox.setChecked(true);
                        } else {
                            checkBox.setChecked(false);
                        }
                        hm.put(item.text, checkBox.isChecked());
                    }
                });
                childController.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        CheckBox checkBox = (CheckBox) arg0.findViewById(R.id.picked_target);
                        if (checkBox.isChecked()) {
                            checkBox.setChecked(false);
                        } else {
                            checkBox.setChecked(true);
                        }
                        hm.put(item.text, checkBox.isChecked());
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }

    private static class ListChildViewHolder extends RecyclerView.ViewHolder {
        public TextView child_title;
        public ImageView child_img;
        public CheckBox child_cb;

        public ListChildViewHolder(View childView) {
            super(childView);
            child_title = childView.findViewById(R.id.tv_name);
            child_img = childView.findViewById(R.id.iv_icon);
            child_cb = childView.findViewById(R.id.picked_target);
        }
    }

    public static class Item {
        public int type;
        public String text;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }
}