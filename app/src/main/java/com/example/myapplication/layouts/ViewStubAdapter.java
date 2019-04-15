package com.example.myapplication.layouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

public class ViewStubAdapter extends RecyclerView.Adapter<ViewStubAdapter.ViewStubHolder> {

    private Context context;
    private final int item_count = 20;

    public ViewStubAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewStubHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_stub_item_layout_01, parent, false);
        return new ViewStubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewStubHolder holder, int position) {
        holder.view_stub_item_layout_01_tv.setText("item\t" + position);
    }

    @Override
    public int getItemCount() {
        return item_count;
    }

    public static class ViewStubHolder extends RecyclerView.ViewHolder {
        private TextView view_stub_item_layout_01_tv;

        public ViewStubHolder(View itemView) {
            super(itemView);
            view_stub_item_layout_01_tv = itemView.findViewById(R.id.view_stub_item_layout_01_tv);
        }
    }
}
