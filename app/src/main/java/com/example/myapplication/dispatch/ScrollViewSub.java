package com.example.myapplication.dispatch;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

public class ScrollViewSub extends ScrollView {

    RecyclerView recyclerView;
    Scroller scroller;
    private float lastPositionY;
    private final String TAG = "ScrollViewSub";

    public ScrollViewSub(Context context) {
        super(context);
    }

    public ScrollViewSub(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public ScrollViewSub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        if (recyclerView == null) {
            View child = (((LinearLayout) getChildAt(0)).getChildAt(1));
            if (child != null && child instanceof RecyclerView)
                recyclerView = (RecyclerView) child;
        }

        float yPosition = ev.getY();
        int eventAction = MotionEvent.ACTION_MASK & ev.getAction();
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                intercept = super.onInterceptTouchEvent(ev);
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (recyclerView != null) {
                    final int firstItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstCompletelyVisibleItemPosition();
                    final int lastItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                    final int itemCount = ((recyclerView.getLayoutManager())).getItemCount();
                    Log.e(TAG, " firstItemPosition=" + firstItemPosition + "\t\tlastItemPosition=" + lastItemPosition + "\t\titemCount=" + itemCount + "\t\tlastPositionY=" + lastPositionY + "\t\tyPosition=" + yPosition);
                    if (firstItemPosition == 0 && lastPositionY < yPosition) {
                        intercept = true;
                    } else if (lastItemPosition == itemCount && lastPositionY > yPosition) {
                        intercept = true;
                    } else {
                        intercept = false;
                    }
                    break;
                }
                intercept = true;
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        lastPositionY = yPosition;
        return intercept;
    }
}
