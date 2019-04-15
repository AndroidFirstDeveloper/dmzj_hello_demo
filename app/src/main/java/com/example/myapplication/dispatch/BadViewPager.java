package com.example.myapplication.dispatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;

public class BadViewPager extends ViewPager {
    private final String TAG = "BadViewPager";
    private Scroller scroller;
    private float lastPositionX;
    private float lastPositionY;

    public BadViewPager(@NonNull Context context) {
        super(context);
    }

    public BadViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        boolean interrupt = false;
        float x = ev.getX();
        float y = ev.getY();
        int eventAction = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                //调用ViewPager的onInterceptTouchEvent方法初始化mActivePointerId
                super.onInterceptTouchEvent(ev);
                interrupt = false;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                    interrupt = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float xdelta = x - lastPositionX;
                float ydelta = y - lastPositionY;
                if (Math.abs(xdelta) > Math.abs(ydelta)) {
                    interrupt = true;
                } else {
                    interrupt = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                interrupt = false;
                break;
        }
        lastPositionX = x;
        lastPositionY = y;
//        Log.e(TAG, "interrupt=" + interrupt);
        return interrupt;
    }
}
