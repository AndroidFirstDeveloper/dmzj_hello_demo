package com.example.myapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;


public  class VPRecyclerView extends RecyclerView {

    public VPRecyclerView(Context context) {
        super(context);
        init();
    }

    public VPRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private int position = 0;

    private void init() {
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(llm);
        SnapHelper snapHelper = new PagerSnapHelper();
//      SnapHelper snapHelper = new LinearSnapHelper(); //一次可滑动多个
        snapHelper.attachToRecyclerView(this);//居中显示RecyclerView
        this.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.HORIZONTAL));
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager){
                    int firs = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    if (position != firs){
                        position = firs;
                        if (onpagerChageListener != null)
                            onpagerChageListener.onPagerChage(position);
                    }
                }
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.requestDisallowInterceptTouchEvent(true); //事件不传递给父布局
        return super.dispatchTouchEvent(ev);
    }

    public void setOnPagerPosition(int position){
//        this.position = position;
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        layoutManager.scrollToPosition(position);
    }

    public int getOnPagerPosition(){
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        return ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
    }


    interface onPagerChageListener{
        void onPagerChage(int position);
    }

    private onPagerChageListener onpagerChageListener;
    public void setOnPagerChageListener(onPagerChageListener onpagerChageListener){
        this.onpagerChageListener = onpagerChageListener;
    }

}
