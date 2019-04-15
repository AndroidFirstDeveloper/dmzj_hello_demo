package com.example.myapplication.recyclerview.define;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class WrapperRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FixedInfo> mHeadersInfo;
    private List<FixedInfo> mFootersInfo;
    private RecyclerView.Adapter mAdapter;
    private PowerfulRecyclerView recyclerView;

    public WrapperRecyclerViewAdapter(PowerfulRecyclerView recyclerView, RecyclerView.Adapter adapter, List<FixedInfo> mHeaderInfo, List<FixedInfo> mFooterInfo) {
        this.mHeadersInfo = mHeaderInfo;
        this.mFootersInfo = mFooterInfo;
        this.mAdapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType >= PowerfulRecyclerView.BASE_FOOTER_VIEW_TYPE) {
            return new WrapperViewHolder(mFootersInfo.get(viewType - PowerfulRecyclerView.BASE_FOOTER_VIEW_TYPE).getView());
        } else if (viewType >= PowerfulRecyclerView.BASE_HEADER_VIEW_TYPE) {
            return new WrapperViewHolder(mHeadersInfo.get(viewType - PowerfulRecyclerView.BASE_HEADER_VIEW_TYPE).getView());
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof WrapperViewHolder)) {
            mAdapter.onBindViewHolder(holder, position - mHeadersInfo.size());
        }
    }

    @Override
    public int getItemCount() {
        return mHeadersInfo.size() + mAdapter.getItemCount() + mFootersInfo.size();
    }

    @Override
    public int getItemViewType(int position) {
        final int mHeaderNum = mHeadersInfo.size();
        final int normalNum = position - mHeaderNum;
        final int adapterCount = mAdapter.getItemCount();

        if (position < mHeaderNum) {
            return mHeadersInfo.get(position).getItemType();
        } else if (normalNum < adapterCount) {
            return mAdapter.getItemViewType(normalNum);
        }
        return mFootersInfo.get(normalNum - adapterCount).getItemType();
    }

    private class WrapperViewHolder extends RecyclerView.ViewHolder {
        public WrapperViewHolder(View itemView) {
            super(itemView);
            if (recyclerView.isStaggered()) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
                params.setFullSpan(true);
                itemView.setLayoutParams(params);
            }
        }
    }
}
