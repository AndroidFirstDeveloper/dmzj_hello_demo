package com.example.myapplication.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class NestItemChildAdapter extends RecyclerView.Adapter<NestItemChildAdapter.ItemChildHolder> {

    private Context context;
    private List<NestModel.ItemModel> list;
    private LayoutInflater layoutInflater;

    NestItemChildAdapter(Context context, List<NestModel.ItemModel> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemChildHolder(layoutInflater.inflate(R.layout.nest_item_child_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemChildHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getPath())
                .into(holder.nest_item_child_layout_iv);

        holder.nest_item_child_layout_tv.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ItemChildHolder extends RecyclerView.ViewHolder {
        ImageView nest_item_child_layout_iv;
        TextView nest_item_child_layout_tv;

        ItemChildHolder(View itemView) {
            super(itemView);
            nest_item_child_layout_iv = itemView.findViewById(R.id.nest_item_child_layout_iv);
            nest_item_child_layout_tv = itemView.findViewById(R.id.nest_item_child_layout_tv);
        }
    }
}
