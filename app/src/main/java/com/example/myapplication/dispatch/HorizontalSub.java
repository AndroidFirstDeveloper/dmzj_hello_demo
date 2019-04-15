package com.example.myapplication.dispatch;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class HorizontalSub extends HorizontalScrollView {

    private final String TAG = "BadViewPager";
    private Scroller scroller;
    private float lastPositionX;
    private float lastPositionY;
    private RecyclerView recyclerView;


    public HorizontalSub(Context context) {
        super(context);
    }

    public HorizontalSub(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public HorizontalSub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean interrupt = false;
        if (recyclerView == null) {
            View child = ((LinearLayout) getChildAt(0)).getChildAt(1);
            if (child != null && child instanceof RecyclerView) {
                recyclerView = (RecyclerView) child;
            }
        }
        float xPosition = ev.getX();

        int eventAction = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                //调用ViewPager的onInterceptTouchEvent方法初始化mActivePointerId
                interrupt = super.onInterceptTouchEvent(ev);
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                    interrupt = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final int firstItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstCompletelyVisibleItemPosition();
                final int lastItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                final int itemCount=((LinearLayoutManager) (recyclerView.getLayoutManager())).getItemCount();
                Log.e(TAG, " firstItemPosition=" + firstItemPosition + "\t\tlastItemPosition=" + lastItemPosition + "\t\titemCount="+itemCount+"\t\tlastPositionX=" + lastPositionX + "\t\txPosition=" + xPosition);
                if (((LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstCompletelyVisibleItemPosition() == 0 && lastPositionX < xPosition) {
                    interrupt = true;
                    break;
                } else if (((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition() == (recyclerView.getLayoutManager().getItemCount() - 1) && lastPositionX > xPosition) {
                    interrupt = true;
                    break;
                }
                interrupt = false;
                break;
            case MotionEvent.ACTION_UP:
                interrupt = false;
                break;
            default:
                break;
        }
        lastPositionX = xPosition;
        return interrupt;
    }
}
