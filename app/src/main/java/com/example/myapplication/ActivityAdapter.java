package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityHolder> {

    private Context context;
    private List<ActivityBean> list;

    public ActivityAdapter(Context context, List<ActivityBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.main_activity_item_layout, parent, false);
        ActivityHolder holder = new ActivityHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(ActivityHolder holder, final int position) {
        if (holder != null) {
            holder.textView.setText(list.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, list.get(position).getActivity());
                    context.startActivity(intent);
                }
            });
        }
    }

    class ActivityHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ActivityHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                textView = (TextView) itemView.findViewById(R.id.main_activity_item_layout_textview);
            }
        }
    }
}
