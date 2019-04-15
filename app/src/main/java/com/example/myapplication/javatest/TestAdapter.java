package com.example.myapplication.javatest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

    private Context context;
    private List<String> list;

    public TestAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        System.out.println("TestAdapter\t\t\t\t\t\tlist=\t\t" + this.list.hashCode());
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_stub_item_layout_01, parent, false);
        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder holder, int position) {
        holder.view_stub_item_layout_01_tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class TestHolder extends RecyclerView.ViewHolder {
        TextView view_stub_item_layout_01_tv;

        public TestHolder(View itemView) {
            super(itemView);
            view_stub_item_layout_01_tv = itemView.findViewById(R.id.view_stub_item_layout_01_tv);
        }
    }
}
