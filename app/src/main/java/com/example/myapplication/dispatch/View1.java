package com.example.myapplication.dispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class View1 extends View {
    private final String TAG = "View1";

    public View1(Context context) {
        super(context);
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public View1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent: -------->" + getActionType(event.getAction()));
        return super.dispatchTouchEvent(event);
//        return true;
//        return false;
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
