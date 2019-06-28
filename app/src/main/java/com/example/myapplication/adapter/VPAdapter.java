package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class VPAdapter extends RecyclerView.Adapter<VPAdapter.VPHolder> {

    private Context context;
    private List<String> list;

    public VPAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VPHolder(LayoutInflater.from(context).inflate(R.layout.vprecyclerview_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VPHolder holder, int position) {
        final int realPos = position % list.size();
        holder.vprecyclerview_item_layout_tv.setText(list.get(realPos));
        String color = "#ff0000";
        switch (realPos) {
            case 0:
                color = "#ff0000";
                break;
            case 1:
                color = "#00ff00";
                break;
            case 2:
                color = "#0000ff";
                break;
            case 3:
                color = "#56ff76";
                break;
            case 4:
                color = "#2267ff";
                break;
        }
        holder.vprecyclerview_item_layout_iv.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public int getItemCount() {
//        return list.size();
        return Integer.MAX_VALUE;
    }

    static class VPHolder extends RecyclerView.ViewHolder {
        private ImageView vprecyclerview_item_layout_iv;
        private TextView vprecyclerview_item_layout_tv;

        public VPHolder(View itemView) {
            super(itemView);
            vprecyclerview_item_layout_iv = itemView.findViewById(R.id.vprecyclerview_item_layout_iv);
            vprecyclerview_item_layout_tv = itemView.findViewById(R.id.vprecyclerview_item_layout_tv);
        }
    }
}
