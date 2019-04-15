package com.example.myapplication.multiclick;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.CustomClickListener;
import com.example.myapplication.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class MultiClickAdapter extends RecyclerView.Adapter<MultiClickAdapter.MultiClickHolder> {

    public List<String> list;
    private Context context;
    private MyListener myListener = new MyListener(this, 1000);
    private final String TAG = "MultiClickAdapter";

    public MultiClickAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MultiClickHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MultiClickHolder(LayoutInflater.from(context).inflate(R.layout.multi_click_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MultiClickHolder holder, int position) {
        holder.multi_click_item_layout_tv.setTag(position);
        holder.multi_click_item_layout_tv.setText(list.get(position));
        holder.multi_click_item_layout_tv.setOnClickListener(myListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void onItemClicked(View v) {
        Log.e(TAG, "onItemClicked: position=" + v.getTag()+"\t\tcontent="+list.get((int)v.getTag()));

    }

    static class MultiClickHolder extends RecyclerView.ViewHolder {
        private TextView multi_click_item_layout_tv;

        public MultiClickHolder(View itemView) {
            super(itemView);
            multi_click_item_layout_tv = itemView.findViewById(R.id.multi_click_item_layout_tv);
        }
    }

    static class MyListener extends CustomClickListener {
        WeakReference<RecyclerView.Adapter> weakReference;

        public MyListener(RecyclerView.Adapter adapter, long time) {
            super(time);
            weakReference = new WeakReference<>(adapter);
        }

        @Override
        protected void onSingleClick(View v) {
            if (weakReference.get() instanceof MultiClickAdapter) {
                ((MultiClickAdapter) weakReference.get()).onItemClicked(v);
            }
        }
    }
}
