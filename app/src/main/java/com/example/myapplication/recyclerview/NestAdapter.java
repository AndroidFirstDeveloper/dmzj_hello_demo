package com.example.myapplication.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class NestAdapter extends RecyclerView.Adapter<NestAdapter.ItemHolder> {

    private Context context;
    private List<NestModel> list;
    private LayoutInflater layoutInflater;
    private RecyclerView.RecycledViewPool recycledViewPool;

    NestAdapter(Context context, List<NestModel> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(layoutInflater.inflate(R.layout.nest_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.nest_item_layout_tv.setText("【" + position + "】");
        holder.nest_item_layout_rv.setTag(position);
        holder.nest_item_layout_rv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        NestItemChildAdapter nestItemChildAdapter = new NestItemChildAdapter(context, list.get(position).getImages());
        holder.nest_item_layout_rv.setRecycledViewPool(recycledViewPool);
        holder.nest_item_layout_rv.setAdapter(nestItemChildAdapter);
        if (list.get(position).getOffset() > 0) {
            Log.e("onBindView", "position=" + position);
            ((LinearLayoutManager) (holder.nest_item_layout_rv.getLayoutManager())).scrollToPositionWithOffset(list.get(position).getPosition(), list.get(position).getOffset());
        }
        holder.nest_item_layout_rv.addOnScrollListener(new MyScrollListener(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        RecyclerView nest_item_layout_rv;
        TextView nest_item_layout_tv;

        ItemHolder(View itemView) {
            super(itemView);
            nest_item_layout_rv = itemView.findViewById(R.id.nest_item_layout_rv);
            nest_item_layout_tv = itemView.findViewById(R.id.nest_item_layout_tv);
        }
    }

    static class MyScrollListener extends RecyclerView.OnScrollListener {
        private NestModel nestModel;
        private int mItemWidth = 0;
        private int mItemMargin = 0;

        public MyScrollListener(NestModel nestModel) {
            this.nestModel = nestModel;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && (nestModel.getIndex() == (int) recyclerView.getTag())) {
                int offset = recyclerView.computeHorizontalScrollOffset();
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() < 0 ? nestModel.getPosition() : ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() + 1;
                nestModel.setPosition(position);
                if (mItemWidth <= 0) {
                    View item = (recyclerView.getLayoutManager()).findViewByPosition(nestModel.getPosition());
                    if (item != null) {
                        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) item.getLayoutParams();
                        mItemWidth = item.getWidth();
                        mItemMargin = layoutParams.rightMargin + layoutParams.leftMargin;
                    }
                }
                if (mItemWidth > 0) {
                    //offset % mItemWidth：得到当前position的滑动距离
                    //mEntity.scrollPosition * mItemMargin：得到（0至position）的所有item的margin
                    //用当前item的宽度-所有margin-当前position的滑动距离，就得到offset。
                    int scrollOffset = mItemWidth - offset % mItemWidth + nestModel.getPosition() * mItemMargin;
                    Log.e("onScrollState", "position=" + position + "\t\toffset=" + scrollOffset + "\t\tindex=" + nestModel.getIndex() + "\t\ttag=" + recyclerView.getTag());
                    nestModel.setOffset(scrollOffset);
                }
            }
        }
    }
}
