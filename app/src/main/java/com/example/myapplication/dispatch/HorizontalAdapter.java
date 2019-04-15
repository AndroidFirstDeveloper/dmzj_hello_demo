package com.example.myapplication.dispatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalHolder> {

    private Context context;
    public HorizontalAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.horizontal_item_layout, parent, false);
        return new HorizontalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalHolder holder, int position) {
        Glide.with(context)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545829476312&di=6b3bd5db729ec2a98728f227d20d4439&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F39%2F82%2F26s58PICD3M_1024.jpg")
                .into(holder.horizontal_item_layout_imageview);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class HorizontalHolder extends RecyclerView.ViewHolder {
        private ImageView horizontal_item_layout_imageview;

        public HorizontalHolder(View itemView) {
            super(itemView);
            horizontal_item_layout_imageview = itemView.findViewById(R.id.horizontal_item_layout_imageview);
        }
    }
}
