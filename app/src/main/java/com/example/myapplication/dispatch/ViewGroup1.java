package com.example.myapplication.dispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ViewGroup1 extends RelativeLayout {

    private final String TAG = "ViewGroup1";

    public ViewGroup1(Context context) {
        super(context);
    }

    public ViewGroup1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroup1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: -------->" + getActionType(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent: -------->" + getActionType(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: -------->" + getActionType(event.getAction()));
        return super.onTouchEvent(event);
//        return true;
    }


    private String getActionType(int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            return "ACTION_DOWN";
        } else if (action == MotionEvent.ACTION_MOVE) {
            return "ACTION_MOVE";
        } else if (action == MotionEvent.ACTION_UP) {
            return "ACTION_UP";
        } else if (action == MotionEvent.ACTION_CANCEL) {
            return "ACTION_CANCEL";
        } else {
            return "Other Action";
        }
    }
}
