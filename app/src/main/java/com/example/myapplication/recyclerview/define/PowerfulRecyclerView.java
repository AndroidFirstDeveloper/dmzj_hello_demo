package com.example.myapplication.recyclerview.define;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PowerfulRecyclerView extends RecyclerView {

    public PowerfulRecyclerView(Context context) {
        super(context);
    }

    public PowerfulRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PowerfulRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private List<FixedInfo> mHeadersInfo;
    private List<FixedInfo> mFootersInfo;

    private void init() {
        if (mHeadersInfo == null) {
            mHeadersInfo = new ArrayList<>();
        }
        if (mFootersInfo == null) {
            mFootersInfo = new ArrayList<>();
        }
    }

    public static final int BASE_HEADER_VIEW_TYPE = 100;
    public static final int BASE_FOOTER_VIEW_TYPE = 200;

    //当添加完header后如何刷新、直接调用最外部设置的adapter通知刷新是否有效
    public void addHeaderView(View view) {
        addHeaderView(view, BASE_HEADER_VIEW_TYPE + mHeadersInfo.size());
    }

    private void addHeaderView(View view, int viewType) {
        FixedInfo fixedInfo = new FixedInfo();
        fixedInfo.setView(view);
        fixedInfo.setItemType(viewType);
        mHeadersInfo.add(fixedInfo);
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        addFooterView(view, BASE_FOOTER_VIEW_TYPE + mFootersInfo.size());
    }

    private void addFooterView(View view, int viewType) {
        FixedInfo fixedInfo = new FixedInfo();
        fixedInfo.setView(view);
        fixedInfo.setItemType(viewType);
        mFootersInfo.add(fixedInfo);

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    private WrapperRecyclerViewAdapter mAdapter;

    private final AdapterDataObserver adapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
//                    super.onChanged();
            Log.e("PowerfulRV", "onChanged()");
            if (mAdapter != null)
                mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            adapter.registerAdapterDataObserver(adapterDataObserver);
            if (mAdapter == null) {
                mAdapter = new WrapperRecyclerViewAdapter(this, adapter, mHeadersInfo, mFootersInfo);
            }
        }
        super.setAdapter(mAdapter);
    }

    private boolean isStaggered = false;

    public boolean isStaggered() {
        return isStaggered;
    }

    public void setStaggered(boolean staggered) {
        isStaggered = staggered;
    }

    @Override
    public void setLayoutManager(final LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {
            ((GridLayoutManager) layout).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position < mHeadersInfo.size() || position >= (mAdapter.getItemCount() - mFootersInfo.size())) {
                        return ((GridLayoutManager) layout).getSpanCount();
                    }
                    return 1;
                }
            });
        }

        if ((layout instanceof StaggeredGridLayoutManager)) {
            setStaggered(true);
        }
        super.setLayoutManager(layout);
    }
}
